import { Link } from "react-router-dom";

function Checkout() {
  return (
    <button type="button" class="btn-buy">
      <Link to="/checkout">Checkout</Link>
    </button>
  );
}

export default Checkout;
