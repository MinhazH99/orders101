import { useContext } from "react";
import BasketClose from "../BasketClose";
import CartBox from "./CartBox";
import Checkout from "./Checkout";
import { ShoppingCartContext } from "../../App";

function Cart({ isOpen, closeCart }) {
  const { cartItems, cartTotal } = useContext(ShoppingCartContext);

  return (
    <div class="cart" style={{ display: isOpen ? "block" : "none" }}>
      <h2 class="card-title">Your Cart</h2>
      <div class="cart-content">
        {cartItems.map((cartItem) => (
          <CartBox
            key={cartItem.id}
            id={cartItem.id}
            name={cartItem.name}
            price={"£" + cartItem.totalCost.toFixed(2)}
            quantity={cartItem.quantity}
          />
        ))}
      </div>
      <div class="total">
        <div class="total__title">
          <h3>Total</h3>
        </div>
        <div class="total__price">{"£" + cartTotal.toFixed(2)}</div>
      </div>
      <Checkout />
      <div id="cart-close">
        <BasketClose closeCart={closeCart} />
      </div>
    </div>
  );
}

export default Cart;
