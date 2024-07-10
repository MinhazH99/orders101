type Props = {
  itemName: string;
  quantity: number;
  price: string;
};

function CustomerOrders({
  itemName = "test",
  quantity = 0,
  price = "x",
}: Props) {
  return (
    <div className="order-box">
      <img src="/trending-product.webp" alt=" " className="order-img"></img>
      <div className="order-box__detail">
        <div className="order-box__name">{itemName}</div>
        <div className="order-box__quantity">Quantity: {quantity}</div>
        <div className="order-box__price">Price: {price}</div>
      </div>
    </div>
  );
}

export default CustomerOrders;
