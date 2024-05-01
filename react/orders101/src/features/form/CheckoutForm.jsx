import { useRef } from "react";
import { submitOrder, updateStock } from "./checkoutService";

function CheckoutForm() {
  const customerFirstName = useRef(null);
  const customerSecondName = useRef(null);
  const customerEmailAddress = useRef(null);
  const deliveryCountry = useRef(null);
  const deliveryStreet = useRef(null);
  const deliveryPostCode = useRef(null);

  function handleClick() {
    updateStock();
    submitOrder({
      firstName: customerFirstName.current.value,
      secondName: customerSecondName.current.value,
      emailAddress: customerEmailAddress.current.value,
      streetName: deliveryStreet.current.value,
      postCode: deliveryPostCode.current.value,
      country: deliveryCountry.current.value,
    });
  }

  return (
    <div>
      <div class="checkout__child">
        <form class="checkout__form">
          <span>1. Customer Details</span>
          <label>
            <span>First Name</span>
          </label>
          <input class="form__first-name" type="text" ref={customerFirstName} />

          <label>
            <span>Second Name</span>
          </label>
          <input
            class="form__second-name"
            type="text"
            ref={customerSecondName}
          />

          <label>
            <span>Email Address</span>
          </label>
          <input
            class="form__email-address"
            type="text"
            ref={customerEmailAddress}
          />
        </form>
      </div>
      <div class="checkout__child">
        <form class="checkout__form">
          <span>2. Delivery Address</span>
          <label>
            <span>Country Region</span>
          </label>
          <input class="form__country" type="text" ref={deliveryCountry} />

          <label>
            <span>Street Name</span>
          </label>
          <input class="form__street-name" type="text" ref={deliveryStreet} />

          <label>
            <span>Post Code</span>
          </label>
          <input class="form__post-code" type="text" ref={deliveryPostCode} />
        </form>
      </div>
      <div class="checkout__child">
        <form class="checkout__form">
          <span>3. Payment Method</span>
          <label>
            <span>Card Number</span>
          </label>
          <input class="text-field" type="text" />

          <label>
            <span>Cardholder Name</span>
          </label>
          <input class="text-field" type="text" />

          <label>
            <span>Expiry Date</span>
          </label>
          <input class="text-field" type="text" />

          <label>
            <span>Security Code</span>
          </label>
          <input class="text-field" type="text" />
        </form>
      </div>
      <div class="checkout__terms-conditions">
        <label>
          <input type="checkbox" />
          <span> I have read and accept the Terms and Conditions</span>
        </label>
        <button onClick={handleClick} class="checkout__submit btn-buy">
          Submit Order
        </button>
      </div>
    </div>
  );
}

export default CheckoutForm;
