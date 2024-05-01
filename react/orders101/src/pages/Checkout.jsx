import CheckoutForm from "../features/form/CheckoutForm";
import CheckoutHeader from "../features/header/CheckoutHeader";
import OrderSummary from "../features/order/OrderSummary";

function Checkout() {
  return (
    <div>
      <CheckoutHeader />
      <div class="container">
        <div class="checkout grid">
          <CheckoutForm />
          <OrderSummary />
        </div>
      </div>
    </div>
  );
}

export default Checkout;
