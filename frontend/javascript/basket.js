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

function removeItemFromCartList(currentCartItemId) {
    const start = cartList.indexOf(currentCartItemId);
    const deleteCount = 1;
    return cartList.splice(start, deleteCount);
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
    let currentQuantity = Number(sessionStorage.getItem(currentProductId));

    let increaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-increase');
    increaseInQuantitybtn.addEventListener('click', function () {
        decreaseQtyImg.className = 'cart-box-btn__enabled';
        currentQuantity += 1;
        sessionStorage.setItem(currentProductId, currentQuantity);
        currentQuantityElement.innerHTML = currentQuantity;
        updateTotal();
    });

    let decreaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-decrease');
    decreaseInQuantitybtn.addEventListener('click', function () {
        const hasMinimumAllowedQuantity = isUpdatedQuantityOne(currentQuantity - 1);

        currentQuantity = decrementQuantity(currentQuantity);
        sessionStorage.setItem(currentProductId, currentQuantity);
        currentQuantityElement.innerHTML = currentQuantity;
        updateTotal();

        decreaseQtyImg.className = hasMinimumAllowedQuantity
            ? 'cart-box-btn__disabled'
            : 'cart-box-btn__enabled';
    });
}

// TODO save quantity in session storage and make the decrease button faded out in index HTLM and when quantity increases turn it normal

function isUpdatedQuantityOne(quantity) {
    return quantity <= 1;
}

function decrementQuantity(quantity) {
    return quantity <= 1 ? 1 : quantity - 1;
}

const supportsTemplate = () => 'content' in document.createElement('template');

let doesBrowserSupportTemplete = supportsTemplate();

function addCartItem(addCartBtn, productList) {
    if (doesBrowserSupportTemplete) {
        let templateClone = createTemplate('#cart-box-template');
        let currentProductId = addCartBtn.getAttribute('data-test');

        if (sessionStorage.getItem(currentProductId) >= 1) {
        } else {
            sessionStorage.setItem(currentProductId, 1);
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
