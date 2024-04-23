import { useContext } from "react";
import { ShoppingCartContext } from "../../App";

function CartBox({ name = "Item Name", price = "£XX.XX", quantity = 1, id }) {
  const { increaseQuantity, decreaseQuantity } =
    useContext(ShoppingCartContext);

  return (
    <div class="cart-box">
      <img
        src="../src/assets/trending-product.webp"
        alt=" "
        class="cart-img"
      ></img>
      <div class="cart-box__detail">
        <div class="cart-box__product-detail">{name}</div>
        <div class="cart-box__product_price">{price}</div>
        <div class="cart-box_quantity-control">
          <button
            onClick={() => decreaseQuantity(id)}
            class="cart-box_quantity-decrease"
          >
            <img
              class="cart-box-btn__disabled"
              src="../src/assets/dash-square.svg"
              alt=""
            ></img>
          </button>
          <div class="cart-box__product-quantity">{quantity}</div>
          <button
            onClick={() => increaseQuantity(id)}
            class="cart-box_quantity-increase"
          >
            <img src="../src/assets/plus-square.svg" alt=""></img>
          </button>
        </div>
      </div>
      <button class="cart-remove">
        <img src="../src/assets/trash3-fill.svg"></img>
      </button>
    </div>
  );
}

export default CartBox;
