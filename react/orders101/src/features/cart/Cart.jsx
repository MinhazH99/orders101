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
            key={cartItem.id}
            id={cartItem.id}
            name={cartItem.name}
            price={"£" + cartItem.unitPrice.toFixed(2)}
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
