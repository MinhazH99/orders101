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

addItemtoOrder();
updateTotalQuantityInOrder();
updateTotalPriceOfOrder();

export { addItemtoOrder, updateTotalQuantityInOrder, updateTotalPriceOfOrder };
