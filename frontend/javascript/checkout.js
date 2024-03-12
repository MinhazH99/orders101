const supportsTemplate = () => 'content' in document.createElement('template');

let doesBrowserSupportTemplete = supportsTemplate();

function createTemplate(element) {
    return document.querySelector(element).content.cloneNode(true);
}

function addItemtoOrder() {
    if (doesBrowserSupportTemplete) {
        let storage = {};
        Object.keys(sessionStorage).forEach((key) => {
            let templateClone = createTemplate('#order-details-template');
            storage[key] = JSON.parse(sessionStorage.getItem(key));
            let productName = storage[key].name;
            let productPrice = storage[key].price;
            productPrice = productPrice.toFixed(2);
            let productQuantity = storage[key].quantity;

            let orderTitleElement = templateClone.querySelector('.order-box__name');
            orderTitleElement.textContent = productName;

            let orderPriceElement = templateClone.querySelector('.order-box__price');
            orderPriceElement.textContent = '£' + productPrice;

            let orderQuantityElement = templateClone.querySelector('.order-box__quantity');
            orderQuantityElement.textContent = 'Quantity: ' + productQuantity;

            const orderContent = document.querySelector('.order-details');
            orderContent.appendChild(templateClone);
        });
    }
}

addItemtoOrder();

export { addItemtoOrder };
