package com.minhaz.orders101.endpoints;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.minhaz.orders101.interfaces.AddressDao;
import com.minhaz.orders101.interfaces.BasketDao;
import com.minhaz.orders101.interfaces.CustomerDao;
import com.minhaz.orders101.interfaces.LineItemDao;
import com.minhaz.orders101.interfaces.OrderDao;
import com.minhaz.orders101.models.Basket;
import com.minhaz.orders101.models.LineItem;
import com.minhaz.orders101.models.Order;
import jakarta.validation.Valid;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.PATCH;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ServerErrorException;
import jakarta.ws.rs.core.Response;
import java.io.IOException;
import java.util.Iterator;
import java.util.*;
import java.util.Map.Entry;
import lombok.extern.slf4j.Slf4j;
import org.javers.core.Javers;
import org.javers.core.JaversBuilder;
import org.javers.core.diff.Diff;
import org.javers.core.diff.changetype.ValueChange;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Path("/orders")
@Consumes("application/json")
@Slf4j
public class OrdersEndpoint {

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

  private void replaceNode(JsonNode node, String fieldName, JsonNode jsonValue) {
    if (node.isObject()) {
      ObjectNode objectNode = (ObjectNode) node;
      Iterator<Map.Entry<String, JsonNode>> fields = objectNode.fields();
      while (fields.hasNext()) {
        Map.Entry<String, JsonNode> entry = fields.next();
        String key = entry.getKey();
        JsonNode value = entry.getValue();

        if (key.equals(fieldName)) {
          System.out.println("\nthis is the key " + key);
          System.out.printf("this it the field" + fieldName);
          objectNode.replace(fieldName, jsonValue);
        } else {
          replaceNode(value, fieldName, jsonValue);
        }
      }
    } else if (node.isArray()) {
      ArrayNode arrayNode = (ArrayNode) node;
      for (int i = 0; i < arrayNode.size(); i++) {
        replaceNode(arrayNode.get(i), fieldName, jsonValue);
      }
    }
  }

  @POST
  @Consumes({"application/json"})
  public Response saveOrder(@Valid Order order) throws ServerErrorException {
    log.info("Post method called with order details{}", order);
    // TODO Remove the validation on the ID
    // TODO Make the ID column auto generated by the database

    addressDao.save(order.getDeliveryAddress());
    customerDao.save(order.getCustomer());
    lineItemDao.saveAll(order.getBasket().getLineItems());
    basketDao.save(order.getBasket());

    dao.save(order);
    return Response.ok().build();
  }

  @GET
  @Produces({"application/json"})
  public Response getOrders() {
    List<Order> orders = dao.findAll();
    if (orders.isEmpty()) {
      return Response.status(Response.Status.NOT_FOUND).build();
    } else {
      return Response.ok().entity(orders).build();
    }
  }


  @PATCH
  @Consumes({"application/json"})
  public Response updateDate(Order orderToUpdate) throws IOException {
    Optional<Order> orderInStorage = dao.findById(orderToUpdate.getOrderId());
    if (orderInStorage.isPresent()) {
      Order oldOrder = orderInStorage.get();
      Diff diff = javers.compare(oldOrder, orderToUpdate);
      System.out.println(diff.prettyPrint());

      objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
      objectMapper.registerModule(new JavaTimeModule());
      JsonNode rootNode = objectMapper.valueToTree(oldOrder);

      findAndUpdate(diff.getChangesByType(ValueChange.class), rootNode);

      Order orderWithUpdatesApplied = objectMapper.treeToValue(rootNode, Order.class);
      dao.save(orderWithUpdatesApplied);

      return Response.ok().build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }

  /**
   * this method assumes that field names in the json are unique at their level. For example, there may be several
   * objects with a property called 'unitPrice' but those will be siblings.
   *
   * @param changesByType the list of changes from javers
   * @param node the current json node
   */
  private void findAndUpdate(List<ValueChange> changesByType, JsonNode node) {
    if (node.isObject()) {
      Iterator<Entry<String, JsonNode>> iter = node.fields();
      while (iter.hasNext()) {
        Entry<String, JsonNode> jsonField = iter.next();
        // if field has more nodes, recurse
        if (jsonField.getValue().isObject())
          findAndUpdate(changesByType, jsonField.getValue());
        if (jsonField.getValue().isArray())
          jsonField.getValue().forEach(n -> findAndUpdate(changesByType, n));
        changesByType.forEach(change -> {
          if (change.getPropertyName().equals(jsonField.getKey())) {
            jsonField.setValue(objectMapper.valueToTree(change.getRight()));
          }
        });
      }
    } else if (node.isArray()) {
      node.forEach(n -> findAndUpdate(changesByType, n));
    }
  }

  @DELETE
  public Response deleteOrder(Order myOrder) {
    log.info("Delete method called on order {}", myOrder);
    dao.deleteById("1");
    return Response.ok().build();
  }

  @GET
  @Path("/{orderId}")
  @Produces({"application/json"})
  public Response getOrder(@PathParam("orderId") String orderId) {
    Optional<Order> order;
    order = dao.findById(orderId);
    if (order.isPresent()) {
      log.info("Get method called to retrieve order with ID = {}. Order details {}", order.get().getOrderId(), order);
      return Response.ok().entity(order).build();
    } else {
      return Response.status(Response.Status.NOT_FOUND).build();
    }
  }
}
