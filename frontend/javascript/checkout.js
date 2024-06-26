import { patchOrder, postOrder } from './fetch.js';
import { buildStockAvailabilityUrl } from './api-utils.js';

const supportsTemplate = (function () {
    return 'content' in document.createElement('template');
})(); // IIFE
const submitBtn = document.querySelector('.checkout__submit');
let total = 0;
let storage = {};
let order = {};

submitBtn.addEventListener('click', function () {
    handlePatchRequest();
    handlePostRequest();
});

function createTemplate(element) {
    return document.querySelector(element).content.cloneNode(true);
}

function addItemtoOrder() {
    if (supportsTemplate) {
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

function handlePatchRequest() {
    Object.keys(sessionStorage).forEach((key) => {
        storage[key] = JSON.parse(sessionStorage.getItem(key));
        let productId = key;
        let qty = storage[key].quantity;

        let apiUrl = buildStockAvailabilityUrl(productId, qty);

        patchOrder(apiUrl);
    });
}

function handlePostRequest() {
    let firstName = document.querySelector('.form__first-name').value;
    let secondName = document.querySelector('.form__second-name').value;
    let emailAddress = document.querySelector('.form__email-address').value;

    let streetName = document.querySelector('.form__street-name').value;
    let postCode = document.querySelector('.form__post-code').value;
    let country = document.querySelector('.form__country').value;

    updateOrder(firstName, secondName, emailAddress, streetName, postCode, country);

    let postURL = 'http://localhost:8080/orders/';
    postOrder(postURL, order);
}

function updateOrder(firstName, secondName, emailAddress, streetName, postCode, country) {
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
    order.paymentStatus = 'AUTHORISED';
    order.orderStatus = 'COMPLETED';

    updateOrderWithCurrentDate(order);
}

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
        name: firstName + ' ' + secondName,
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
}

function updateOrderWithCurrentDate(order) {
    const today = new Date();

    order.createdDate = formatDate(today);
}

addItemtoOrder();
updateTotalQuantityInOrder();
updateTotalPriceOfOrder();
