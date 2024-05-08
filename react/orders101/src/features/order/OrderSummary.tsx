import CustomerOrders from "./CustomerOrders";

function OrderSummary() {
  let storage = [];
  let total = 0;
  Object.keys(sessionStorage).forEach((key) => {
    const order = JSON.parse(sessionStorage.getItem(key));
    storage.push(order);
    total += order.totalCost;
  });

  const listOfOrders = storage.map((order) => (
    <CustomerOrders
      key={order.id}
      itemName={order.name}
      quantity={order.quantity}
      price={"£" + order.totalCost.toFixed(2)}
    />
  ));

  return (
    <div className="checkout__child">
      Order Details
      <div className="checkout__child__header">
        <span>Order Summary</span>
        <span className="checkout_total-quantity">
          {" "}
          {sessionStorage.length} items
        </span>
      </div>
      <div className="checkout__order-number">
        <span>Order Number:</span>
      </div>
      <div className="order-details">{listOfOrders}</div>
      <div className="checkout__total-detail">
        <span className="checkout__total-text">Total</span>
        <span className="checkout__total-price">{"£" + total.toFixed(2)}</span>
      </div>
    </div>
  );
}

export default OrderSummary;
