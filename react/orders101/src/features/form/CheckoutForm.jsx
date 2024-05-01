import { useEffect, useRef } from "react";

function CheckoutForm() {
  const customerFirstName = useRef(null);
  const customerSecondName = useRef(null);
  const customerEmailAddress = useRef(null);
  const deliveryCountry = useRef(null);
  const deliveryStreet = useRef(null);
  const deliveryPostCode = useRef(null);

  let storage = {};
  let order = {};
  let total = 0;

  function handleClick() {
    handlePatchRequest();
    handlePostRequest();
    console.log(JSON.stringify(order));
  }

  function buildStockAvailabilityUrl(productId, qty) {
    return (
      "http://localhost:8081/products/stock-availability/" +
      productId +
      "?inc=false" +
      "&qty=" +
      qty
    );
  }

  function patchOrder(url) {
    fetch(url, {
      method: "PATCH",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  }

  function handlePatchRequest() {
    Object.keys(sessionStorage).forEach((key) => {
      storage[key] = JSON.parse(sessionStorage.getItem(key));
      let productId = key;
      let qty = storage[key].quantity;
      let unitCost = storage[key].totalCost;
      total += unitCost;

      let apiUrl = buildStockAvailabilityUrl(productId, qty);

      patchOrder(apiUrl);
    });
  }

  function handlePostRequest() {
    let firstName = customerFirstName.current.value;
    let secondName = customerSecondName.current.value;
    let emailAddress = customerEmailAddress.current.value;

    let streetName = deliveryStreet.current.value;
    let postCode = deliveryPostCode.current.value;
    let country = deliveryCountry.current.value;

    updateOrder(
      firstName,
      secondName,
      emailAddress,
      streetName,
      postCode,
      country
    );

    let postURL = "http://localhost:8080/orders/";
    postOrder(postURL, order);
  }

  function postOrder(url, order) {
    fetch(url, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(order),
    })
      .then((response) => response.json())
      .then((data) => console.log(data));
  }

  function updateOrder(
    firstName,
    secondName,
    emailAddress,
    streetName,
    postCode,
    country
  ) {
    updateOrderWithCustomerDetails(
      order,
      firstName,
      secondName,
      emailAddress,
      streetName,
      postCode,
      country
    );

    updateOrderWithDeliveryAddress(order, streetName, postCode, country);

    updateOrderWithCartItems(order, storage);

    order.totalPrice = total;
    order.paymentStatus = "AUTHORISED";
    order.orderStatus = "COMPLETED";

    updateOrderWithCurrentDate(order);
  }

  function formatDate(date) {
    let mm = date.getMonth();
    if (mm < 10) {
      mm = "0" + mm;
    }
    let dd = date.getDate();
    if (dd < 10) {
      dd = "0" + dd;
    }
    let yyyy = date.getFullYear();

    return yyyy + "-" + mm + "-" + dd;
  }

  function updateOrderWithCustomerDetails(
    order,
    firstName,
    secondName,
    emailAddress,
    streetName,
    postCode,
    country
  ) {
    order.customer = {
      name: firstName + " " + secondName,
      email: emailAddress,
      invoiceAddress: {
        addressLine1: streetName,
        postCode: postCode,
        country: country,
      },
    };
  }

  function updateOrderWithDeliveryAddress(
    order,
    streetName,
    postCode,
    country
  ) {
    order.deliveryAddress = {
      addressLine1: streetName,
      postCode: postCode,
      country: country,
    };
  }

  function updateOrderWithCartItems(order, storage) {
    order.basket = { lineItems: [] };
    Object.keys(sessionStorage).forEach((key) => {
      storage[key] = JSON.parse(sessionStorage.getItem(key));
      let name = storage[key].name;
      let unitPrice = storage[key].unitPrice;
      let quantity = storage[key].quantity;
      order.basket.lineItems.push({
        name: name,
        description: "test",
        unitPrice: unitPrice,
        quantity: quantity,
      });
    });
  }

  function updateOrderWithCurrentDate(order) {
    const today = new Date();

    order.createdDate = formatDate(today);
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
        <button onClick={() => handleClick()} class="checkout__submit btn-buy">
          Submit Order
        </button>
      </div>
    </div>
  );
}

export default CheckoutForm;
