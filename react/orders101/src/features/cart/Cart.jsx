import { useContext } from "react";
import BasketClose from "../BasketClose";
import CartBox from "./CartBox";
import Checkout from "./Checkout";
import { ShoppingCartContext } from "../../App";

function Cart() {
  const { cartItems } = useContext(ShoppingCartContext);

  return (
    <div class="cart">
      <h2 class="card-title">Your Cart</h2>
      <div class="cart-content">
        {cartItems.map((cartItem) => (
          <CartBox
            name={cartItem.name}
            unitPrice={cartItem.unitPrice}
            quantity={cartItem.quantity}
          />
        ))}
      </div>
      <div class="total">
        <div class="total__title">
          <h3>Total</h3>
        </div>
        <div class="total__price">£0</div>
      </div>
      <Checkout />
      <div id="cart-close">
        <BasketClose />
      </div>
    </div>
  );
}

export default Cart;
