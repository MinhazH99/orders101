import { useContext } from "react";
import { ShoppingCartContext } from "../../App";

function CartBox({ name = "Item Name", price = "£XX.XX", quantity = 1, id }) {
  const { increaseQuantity, decreaseQuantity, removeItem } =
    useContext(ShoppingCartContext);

  return (
    <div className="cart-box">
      <img src="/trending-product.webp" alt=" " className="cart-img"></img>
      <div className="cart-box__detail">
        <div className="cart-box__product-detail">{name}</div>
        <div className="cart-box__product_price">{price}</div>
        <div className="cart-box_quantity-control">
          <button
            onClick={() => decreaseQuantity(id)}
            className="cart-box_quantity-decrease"
          >
            <img
              className="cart-box-btn__disabled"
              src="/dash-square.svg"
              alt=""
            ></img>
          </button>
          <div className="cart-box__product-quantity">{quantity}</div>
          <button
            onClick={() => increaseQuantity(id)}
            className="cart-box_quantity-increase"
          >
            <img src="/plus-square.svg" alt=""></img>
          </button>
        </div>
      </div>
      <button onClick={() => removeItem(id)} className="cart-remove">
        <img src="/trash3-fill.svg"></img>
      </button>
    </div>
  );
}

export default CartBox;
