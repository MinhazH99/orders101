const supportsTemplate = () => 'content' in document.createElement('template');

let doesBrowserSupportTemplete = supportsTemplate();

function createTemplate(element) {
    return document.querySelector(element).content.cloneNode(true);
}

let total = 0;

function addItemtoOrder() {
    if (doesBrowserSupportTemplete) {
        let storage = {};

        Object.keys(sessionStorage).forEach((key) => {
            let templateClone = createTemplate('#order-details-template');
            storage[key] = JSON.parse(sessionStorage.getItem(key));
            let productName = storage[key].name;
            let productTotalCost = storage[key].totalCost;
            let productQuantity = storage[key].quantity;
            total += Number(productTotalCost);

            updateOrderDetails(templateClone, productName, productQuantity, productTotalCost);

            const orderContent = document.querySelector('.order-details');
            orderContent.appendChild(templateClone);
        });
    }
}

function updateOrderDetails(templateClone, productName, productQuantity, productTotalCost) {
    let orderTitleElement = templateClone.querySelector('.order-box__name');
    orderTitleElement.textContent = productName;

    let orderPriceElement = templateClone.querySelector('.order-box__price');
    orderPriceElement.textContent = '£' + productTotalCost;

    let orderQuantityElement = templateClone.querySelector('.order-box__quantity');
    orderQuantityElement.textContent = 'Quantity: ' + productQuantity;
}

function updateTotalQuantityInOrder() {
    let orderTotalQuantityElement = document.querySelector('.checkout_total-quantity');
    orderTotalQuantityElement.textContent = Object.keys(sessionStorage).length + ' items';
}

function updateTotalPriceOfOrder() {
    let totalCartPriceElement = document.querySelector('.checkout__total-price');
    total = total.toFixed(2);
    totalCartPriceElement.textContent = '£' + total;
}

const submitBtn = document.querySelector('.checkout__submit');

function buildStockAvailabilityUrl(productId, qty) {
    return (
        'http://localhost:8081/products/stock-availability/' +
        productId +
        '?inc=false' +
        '&qty=' +
        qty
    );
}

submitBtn.addEventListener('click', function () {
    let storage = {};
    Object.keys(sessionStorage).forEach((key) => {
        storage[key] = JSON.parse(sessionStorage.getItem(key));
        let productId = key;
        let qty = storage[key].quantity;

        let apiUrl = buildStockAvailabilityUrl(productId, qty);

        fetch(apiUrl, {
            method: 'PATCH',
            headers: {
                'Content-Type': 'application/json',
            },
        })
            .then((response) => response.json())
            .then((data) => console.log(data));
    });

    let order = {
        basket: {
            lineItems: [],
        },
        totalPrice: null,
        customer: {
            invoiceAddress: null,
            name: null,
            email: null,
        },
        paymentStatus: null,
        orderStatus: null,
        deliveryAddress: null,
        createdDate: null,
    };

    let firstName = document.querySelector('.form__first-name').value;
    let secondName = document.querySelector('.form__second-name').value;
    let emailAddress = document.querySelector('.form__email-address').value;

    let streetName = document.querySelector('.form__street-name').value;
    let postCode = document.querySelector('.form__post-code').value;
    let country = document.querySelector('.form__country').value;

    order.customer.name = firstName + ' ' + secondName;
    order.customer.email = emailAddress;
    order.customer.invoiceAddress = {
        addressLine1: streetName,
        postCode: postCode,
        country: country,
    };

    order.deliveryAddress = {
        addressLine1: streetName,
        postCode: postCode,
        country: country,
    };

    Object.keys(sessionStorage).forEach((key) => {
        storage[key] = JSON.parse(sessionStorage.getItem(key));
        let name = storage[key].name;
        let unitPrice = storage[key].unitPrice;
        let quantity = storage[key].quantity;

        order.basket.lineItems.push({
            name: name,
            description: 'test',
            unitPrice: unitPrice,
            quantity: quantity,
        });
    });

    order.totalPrice = total;
    order.paymentStatus = 'AUTHORISED';
    order.orderStatus = 'COMPLETED';

    const today = new Date();

    order.createdDate = formatDate(today);

    let postURL = 'http://localhost:8080/orders/';
    fetch(postURL, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(order),
    })
        .then((response) => response.json())
        .then((data) => console.log(data));
});

function formatDate(date) {
    let mm = date.getMonth() + 1;
    if (mm < 10) {
        mm = '0' + mm;
    }
    let dd = date.getDate() - 1;
    if (dd < 10) {
        dd = '0' + dd;
    }
    let yyyy = date.getFullYear();

    return yyyy + '-' + mm + '-' + dd;
}

addItemtoOrder();
updateTotalQuantityInOrder();
updateTotalPriceOfOrder();

//Steps in order to send order POST request

// First create an Order object which has the following keys: basket, total price, customer, payment status, order status, delivery address and created date.

// let order = {
//     basket: null,
//     totalPrice: null,
//     customer: null,
//     name: null,
//     email: null,
//     paymentStatus: null,
//     orderStatus: null,
//     deliveryAddress: null,
//     createdDate: null,
// };

//  Retreive address details using form and assign them to delivery and invoice address

// Then create a customer object where the value of "invoice address key" is the address object

// Then need to create array of objects where each object contains the name, description, unit price and quanity of the line item

// Set the array as a value for the line item key

// Set the line item as a value for the basket key

// Set the value of total price using the total variable set above

// Set paymenet status to AUTHROISED

// Set order status to COMPLETED

// Set name and email using the form

// Set created date to current date

// Convert the Order object into JSON and pass it in the POST request

export { addItemtoOrder, updateTotalQuantityInOrder, updateTotalPriceOfOrder };
