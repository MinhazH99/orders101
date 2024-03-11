import { createTemplate } from './products.js';

let cartList = [];

function removeCartItem(templateClone2, currentProductId) {
    let cartRemove_btn = templateClone2[templateClone2.length - 1].querySelector('.cart-remove');

    cartRemove_btn.addEventListener('click', function () {
        sessionStorage.removeItem(currentProductId);
        cartRemove_btn.parentElement.remove();
        updateTotal();
    });
}

function updateTotal() {
    let cartBoxes = document.querySelector('.cart-content').querySelectorAll('.cart-box');

    let totalPrice = document.querySelector('.total__price');
    let total = 0;

    cartBoxes.forEach((cartBox) => {
        let priceElement = cartBox.querySelector('.cart-box__product_price');
        let price = parseFloat(priceElement.innerHTML.replace('£', ''));
        let quantity = cartBox.querySelector('.cart-box__product-quantity').innerHTML;
        total += price * quantity;
    });

    total = total.toFixed(2);

    totalPrice.innerHTML = '£' + total;
}

function initiateQuantityButtons(templateClone2, currentProductId) {
    let cartBox = templateClone2[templateClone2.length - 1].querySelector(
        '.cart-box_quantity-control'
    );

    let decreaseQtyImg = cartBox.querySelector('.cart-box-btn__disabled ');
    let currentQuantityElement = cartBox.querySelector('.cart-box__product-quantity');

    let increaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-increase');
    increaseInQuantitybtn.addEventListener('click', function () {
        increaseQuantity(decreaseQtyImg, currentProductId, currentQuantityElement);
    });

    let decreaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-decrease');
    decreaseInQuantitybtn.addEventListener('click', function () {
        decreaseQuantity(currentQuantityElement, currentProductId, decreaseQtyImg);
    });
}

function isUpdatedQuantityOne(quantity) {
    return quantity <= 1;
}

function decrementQuantity(quantity) {
    return quantity <= 1 ? 1 : quantity - 1;
}

function increaseQuantity(decreaseQtyImg, currentProductId, currentQuantityElement) {
    decreaseQtyImg.className = 'cart-box-btn__enabled';
    let cartItemAsString = JSON.parse(sessionStorage.getItem(currentProductId));
    let currentQuantity = Number(cartItemAsString.quantity);
    currentQuantity += 1;
    cartItemAsString.quantity = currentQuantity;
    sessionStorage.setItem(currentProductId, JSON.stringify(cartItemAsString));
    currentQuantityElement.innerHTML = currentQuantity;
    updateTotal();
}

function decreaseQuantity(currentQuantityElement, currentProductId, decreaseQtyImg) {
    let cartItemAsString = JSON.parse(sessionStorage.getItem(currentProductId));
    let currentQuantity = Number(cartItemAsString.quantity);
    const hasMinimumAllowedQuantity = isUpdatedQuantityOne(currentQuantity - 1);

    currentQuantity = decrementQuantity(currentQuantity);
    cartItemAsString.quantity = currentQuantity;
    sessionStorage.setItem(currentProductId, JSON.stringify(cartItemAsString));
    currentQuantityElement.innerHTML = currentQuantity;
    updateTotal();

    decreaseQtyImg.className = hasMinimumAllowedQuantity
        ? 'cart-box-btn__disabled'
        : 'cart-box-btn__enabled';
}

const supportsTemplate = () => 'content' in document.createElement('template');

let doesBrowserSupportTemplete = supportsTemplate();

function addCartItem(addCartBtn, productList) {
    if (doesBrowserSupportTemplete) {
        let templateClone = createTemplate('#cart-box-template');
        let currentProductId = addCartBtn.getAttribute('data-product-id');
        let cartItem = {
            name: productList[currentProductId].productName,
            price: productList[currentProductId].productUnitPrice,
            quantity: 1,
        };

        if (sessionStorage.getItem(currentProductId) < 1) {
            sessionStorage.setItem(currentProductId, JSON.stringify(cartItem));
            appendProductTitle(templateClone, currentProductId, productList);

            appendProductPrice(templateClone, currentProductId, productList);

            const cartContent = document.querySelector('.cart-content');
            cartContent.appendChild(templateClone);

            let templateClone2 = cartContent.querySelectorAll('.cart-box');
            updateTotal();
            removeCartItem(templateClone2, currentProductId);
            initiateQuantityButtons(templateClone2, currentProductId);
        }
    }
}

function appendProductTitle(templateClone, currentProductId, productList) {
    let productTitleElement = templateClone.querySelector('.cart-box__product-detail');
    let productName = productList[currentProductId].productName;
    productTitleElement.textContent = productName;
}

function appendProductPrice(templateClone, currentProductId, productList) {
    let productPriceElement = templateClone.querySelector('.cart-box__product_price');
    let productPrice = productList[currentProductId].productUnitPrice.toFixed(2);
    productPriceElement.textContent = '£' + productPrice;
}

export { removeCartItem, updateTotal, initiateQuantityButtons, isUpdatedQuantityOne, addCartItem };
