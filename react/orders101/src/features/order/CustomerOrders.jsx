function CustomerOrders({ itemName = "test", quantity = "x", price = "x" }) {
  return (
    <div class="order-box">
      <img
        src="../src/assets/trending-product.webp"
        alt=" "
        class="order-img"
      ></img>
      <div class="order-box__detail">
        <div class="order-box__name">{itemName}</div>
        <div class="order-box__quantity">Quantity: {quantity}</div>
        <div class="order-box__price">Price: {price}</div>
      </div>
    </div>
  );
}

export default CustomerOrders;
