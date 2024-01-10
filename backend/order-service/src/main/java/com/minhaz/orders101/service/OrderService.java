package com.minhaz.orders101.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.dao.AddressDao;
import com.minhaz.orders101.dao.BasketDao;
import com.minhaz.orders101.dao.CustomerDao;
import com.minhaz.orders101.dao.LineItemDao;
import com.minhaz.orders101.dao.OrderDao;
import com.minhaz.orders101.model.Order;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class OrderService {

  @Autowired
  private OrderDao dao;

  @Autowired
  private AddressDao addressDao;

  @Autowired
  private CustomerDao customerDao;

  @Autowired
  private LineItemDao lineItemDao;

  @Autowired
  private BasketDao basketDao;
  private final Javers javers = JaversBuilder.javers().build();
  private final ObjectMapper objectMapper = new ObjectMapper();

  public void persist(Order order) {
    addressDao.save(order.getCustomer().getInvoiceAddress());
    addressDao.save(order.getDeliveryAddress());
    customerDao.save(order.getCustomer());
    lineItemDao.saveAll(order.getBasket().getLineItems());
    basketDao.save(order.getBasket());
    dao.save(order);
  }

  public List<Order> retrieveAll() {
    return dao.findAll();
  }

  public Optional<Order> retrieveById(String id) {
    return dao.findById(id);
  }

  public boolean orderRequiresUpdate(Order oldOrder, Order newOrder) {
    Diff diff = javers.compare(oldOrder, newOrder);
    System.out.println(diff.prettyPrint());
    return diff.hasChanges();
  }


  /**
   * this method assumes that field names in the json are unique at their level. For example, there may be several
   * objects with a property called 'unitPrice' but those will be siblings.
   *
   * @param changesByType the list of changes from javers
   * @param node the current json node
   */
  private void findAndUpdate(List<ValueChange> changesByType, JsonNode node) {
    // loop through the changes
    changesByType.forEach(change -> {
      var changedProperty = change.getPropertyName();
      var changedId = change.getAffectedLocalId();
      var parents = node.findParents(changedProperty);
      parents.forEach(parent -> {
        if (parent.get("id").textValue().equals(changedId) && parent.get(changedProperty) != null) {
          amendNode(parent, change);
        }
      });
    });
  }

  public Order applyDiff(Order updatedOrder, Order existingOrder) throws JsonProcessingException {
    Diff diff = javers.compare(existingOrder, updatedOrder);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());
    objectMapper.registerModule(new JavaTimeModule());
    JsonNode rootNode = objectMapper.valueToTree(existingOrder);
    findAndUpdate(diff.getChangesByType(ValueChange.class), rootNode);
    return objectMapper.treeToValue(rootNode, Order.class);
  }

  private void amendNode(JsonNode parent, ValueChange change) {
    Iterator<Entry<String, JsonNode>> iter = parent.fields();
    while (iter.hasNext()) {
      Entry<String, JsonNode> jsonField = iter.next();
      if (change.getPropertyName().equals(jsonField.getKey())) {
        jsonField.setValue(objectMapper.valueToTree(change.getRight()));
        break;
      }
    }
  }

  public void delete(String id) {
    dao.deleteById(id);
  }
}
