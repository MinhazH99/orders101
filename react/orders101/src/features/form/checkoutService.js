let order = {};
let storage = {};
let total = 0;

function submitOrder({
  firstName,
  secondName,
  emailAddress,
  streetName,
  postCode,
  country,
}) {
  updateOrder({
    firstName,
    secondName,
    emailAddress,
    streetName,
    postCode,
    country,
  });

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

function updateOrder({
  firstName,
  secondName,
  emailAddress,
  streetName,
  postCode,
  country,
}) {
  updateOrderWithCustomerDetails({
    order,
    firstName,
    secondName,
    emailAddress,
    streetName,
    postCode,
    country,
  });

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

function updateOrderWithCustomerDetails({
  order,
  firstName,
  secondName,
  emailAddress,
  streetName,
  postCode,
  country,
}) {
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

function updateOrderWithDeliveryAddress(order, streetName, postCode, country) {
  order.deliveryAddress = {
    addressLine1: streetName,
    postCode: postCode,
    country: country,
  };
}

function updateOrderWithCartItems(order, storage) {
  order.basket = { lineItems: [] };
  Object.keys(sessionStorage).forEach((key) => {
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

function updateStock() {
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

export { submitOrder, updateStock };
