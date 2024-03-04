import { createTemplate } from './products.js';

let cartList = [];

function removeCartItem(addCartBtn) {
    let cartRemove_btn = document.querySelector('.cart-content').querySelectorAll('.cart-remove');

    cartRemove_btn.forEach((btn) => {
        btn.addEventListener('click', function () {
            let currentCartItemId = addCartBtn.getAttribute('data-test');
            removeItemFromCartList(currentCartItemId);
            btn.parentElement.remove();
            updateTotal();
        });
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

function initiateQuantityButtons() {
    let cartBoxes = document
        .querySelector('.cart-content')
        .querySelectorAll('.cart-box_quantity-control');

    cartBoxes.forEach((cartBox) => {
        let decreaseQtyImg = cartBox.querySelector('.cart-box-btn__disabled ');
        let currentQuantityElement = cartBox.querySelector('.cart-box__product-quantity');
        let currentQuantity = Number(currentQuantityElement.innerHTML);

        let increaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-increase');
        increaseInQuantitybtn.addEventListener('click', function () {
            decreaseQtyImg.className = 'cart-box-btn__enabled';
            currentQuantity += 1;
            currentQuantityElement.innerHTML = currentQuantity;
            updateTotal();
        });

        let decreaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-decrease');
        decreaseInQuantitybtn.addEventListener('click', function () {
            if (isUpdatedQuantityOne(currentQuantity - 1)) {
                currentQuantity = decrementQuantity(currentQuantity);
                currentQuantityElement.innerHTML = currentQuantity;
                updateTotal();
                decreaseQtyImg.className = 'cart-box-btn__disabled';
            } else {
                decreaseQtyImg.className = 'cart-box-btn__enabled';
                currentQuantity = decrementQuantity(currentQuantity);
                currentQuantityElement.innerHTML = currentQuantity;
                updateTotal();
            }
        });
    });
}

// TODO save quantity in session storage and make the decrease button faded out in index HTLM and when quantity increases turn it normal

function isUpdatedQuantityOne(quantity) {
    if (quantity <= 1) {
        return true;
    }
    return false;
}

function decrementQuantity(quantity) {
    if (quantity <= 1) {
        return 1;
    } else {
        return quantity - 1;
    }
}

const supportsTemplate = () => 'content' in document.createElement('template');

let doesBrowserSupportTemplete = supportsTemplate();

function addCartItem(addCartBtn, productList) {
    if (doesBrowserSupportTemplete) {
        let templateClone = createTemplate('#cart-box-template');
        let currentProductId = addCartBtn.getAttribute('data-test');

        if (cartList.includes(currentProductId)) {
        } else {
            cartList.push(currentProductId);
            appendProductTitle(templateClone, addCartBtn, productList);

            appendProductPrice(templateClone, addCartBtn, productList);

            const cartContent = document.querySelector('.cart-content');
            cartContent.appendChild(templateClone);

            updateTotal();
            removeCartItem(addCartBtn);
            initiateQuantityButtons(templateClone);
        }
    }
}

function appendProductTitle(templateClone, addCartBtn, productList) {
    let productTitleElement = templateClone.querySelector('.cart-box__product-detail');
    let productId = addCartBtn.getAttribute('data-test');
    let productName = productList[productId].productName;
    productTitleElement.textContent = productName;
}

function appendProductPrice(templateClone, addCartBtn, productList) {
    let productPriceElement = templateClone.querySelector('.cart-box__product_price');
    let productId = addCartBtn.getAttribute('data-test');
    let productPrice = productList[productId].productUnitPrice.toFixed(2);
    productPriceElement.textContent = '£' + productPrice;
}

export {
    removeCartItem,
    updateTotal,
    initiateQuantityButtons as changeQuantity,
    isUpdatedQuantityOne,
    addCartItem,
};
