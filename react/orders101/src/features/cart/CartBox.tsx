import { useContext } from "react";
import { ShoppingCartContext } from "../../App";

type Props = {
  name: string;
  price: string;
  quantity: number;
  id: string;
};

function CartBox({
  name = "Item Name",
  price = "£XX.XX",
  quantity = 1,
  id,
}: Props) {
  const { increaseQuantity, decreaseQuantity, removeItem } =
    useContext(ShoppingCartContext);

  return (
    <div className="cart-box">
      <img
        src="../src/assets/trending-product.webp"
        alt=" "
        className="cart-img"
      ></img>
      <div className="cart-box__detail">
        <div className="cart-box__product-detail">{name}</div>
        <div className="cart-box__product_price">{price}</div>
        <div className="cart-box_quantity-control">
          <button
            onClick={() => decreaseQuantity(String(id))}
            className="cart-box_quantity-decrease"
          >
            <img
              className="cart-box-btn__disabled"
              src="../src/assets/dash-square.svg"
              alt=""
            ></img>
          </button>
          <div className="cart-box__product-quantity">{quantity}</div>
          <button
            onClick={() => increaseQuantity(String(id))}
            className="cart-box_quantity-increase"
          >
            <img src="../src/assets/plus-square.svg" alt=""></img>
          </button>
        </div>
      </div>
      <button onClick={() => removeItem(String(id))} className="cart-remove">
        <img src="../src/assets/trash3-fill.svg"></img>
      </button>
    </div>
  );
}

export default CartBox;
