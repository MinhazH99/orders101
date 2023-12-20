package com.minhaz.orders101.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.json.JsonWriteFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Optional;

import com.minhaz.orders101.dao.ProductDao;
import com.minhaz.orders101.model.Product;
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
public class ProductService {

  @Autowired
  ProductDao productDao;
  private final Javers javers = JaversBuilder.javers().build();
  private final ObjectMapper objectMapper = new ObjectMapper();

  public void persist(Product product) {
    productDao.save(product);
  }

  public List<Product> retrieveAll() {
    return productDao.findAll();
  }

  public Optional<Product> retrieveById(String id) {
    return productDao.findById(id);
  }

  public boolean productRequiresUpdate(Product oldProduct, Product newProduct) {
    Diff diff = javers.compare(oldProduct, newProduct);
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

  public Product applyDiff(Product updatedProduct, Product existingProduct) throws JsonProcessingException {
    Diff diff = javers.compare(existingProduct, updatedProduct);
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(JsonWriteFeature.QUOTE_FIELD_NAMES.mappedFeature());
    objectMapper.registerModule(new JavaTimeModule());
    JsonNode rootNode = objectMapper.valueToTree(existingProduct);
    findAndUpdate(diff.getChangesByType(ValueChange.class), rootNode);
    return objectMapper.treeToValue(rootNode, Product.class);
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
    productDao.deleteById(id);
  }
}
