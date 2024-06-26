import { buildStockAvailabilityUrl } from './api-utils.js';
import { createTemplate } from './products.js';

let total = 0;
const supportsTemplate = (function () {
    return 'content' in document.createElement('template');
})(); // IIFE

function removeCartItem(templateClone2, currentProductId) {
    let cartRemove_btn = templateClone2[templateClone2.length - 1].querySelector('.cart-remove');

    cartRemove_btn.addEventListener('click', function () {
        updateTotal('delete', currentProductId);
        sessionStorage.removeItem(currentProductId);
        cartRemove_btn.parentElement.remove();
    });
}

function updateTotal(variation, currentProductId) {
    let totalPrice = document.querySelector('.total__price');
    let currentProductJSON = JSON.parse(sessionStorage.getItem(currentProductId));
    let currentProductUnitCost = Number(currentProductJSON.unitPrice);
    let currentProductTotalCost = Number(currentProductJSON.totalCost);
    if (variation == 'increment') {
        total += currentProductUnitCost;
    } else if (variation == 'decrement') {
        total -= currentProductUnitCost;
    } else if (variation == 'delete') {
        total -= currentProductTotalCost;
    }
    let formattedTotal = total.toFixed(2);
    totalPrice.innerHTML = '£' + formattedTotal;
}

function initiateQuantityButtons(templateClone2, currentProductId) {
    let cartBox = templateClone2[templateClone2.length - 1].querySelector(
        '.cart-box_quantity-control'
    );
    let currentUnitCostElement = templateClone2[templateClone2.length - 1].querySelector(
        '.cart-box__product_price'
    );
    let decreaseQtyImg = cartBox.querySelector('.cart-box-btn__disabled ');
    let currentQuantityElement = cartBox.querySelector('.cart-box__product-quantity');
    let increaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-increase');
    increaseInQuantitybtn.addEventListener('click', function () {
        fetch(
            buildStockAvailabilityUrl(
                currentProductId,
                JSON.parse(sessionStorage.getItem(currentProductId)).quantity + 1
            ),
            {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json',
                },
            }
        )
            .then((response) => response.json())
            .then((data) => {
                if (getStockAvaiabilityResp(data)) {
                    increaseQuantity(
                        decreaseQtyImg,
                        currentProductId,
                        currentQuantityElement,
                        currentUnitCostElement
                    );
                } else {
                    increaseInQuantitybtn.className = 'cart-box-btn__disabled';
                    increaseInQuantitybtn.setAttribute('title', 'No stock available');
                }
            });
    });

    let decreaseInQuantitybtn = cartBox.querySelector('.cart-box_quantity-decrease');
    decreaseInQuantitybtn.addEventListener('click', function () {
        decreaseQuantity(
            currentQuantityElement,
            currentProductId,
            decreaseQtyImg,
            currentUnitCostElement
        );
    });
}

function getStockAvaiabilityResp(data) {
    if (data.data.available == true) {
        return true;
    }
}

function isUpdatedQuantityOne(quantity) {
    return quantity <= 1;
}

function decrementQuantity(quantity) {
    return quantity <= 1 ? 1 : quantity - 1;
}

function increaseQuantity(
    decreaseQtyImg,
    currentProductId,
    currentQuantityElement,
    currentUnitCostElement
) {
    decreaseQtyImg.className = 'cart-box-btn__enabled';
    let cartItemAsString = JSON.parse(sessionStorage.getItem(currentProductId));
    let currentQuantity = Number(cartItemAsString.quantity);
    currentQuantity += 1;
    let updatedTotalCost = calculateAndSetProductTotalCost(
        cartItemAsString,
        currentQuantity,
        currentProductId
    );
    currentUnitCostElement.textContent = '£' + updatedTotalCost;
    currentQuantityElement.textContent = currentQuantity;
    updateTotal('increment', currentProductId);
}

function decreaseQuantity(
    currentQuantityElement,
    currentProductId,
    decreaseQtyImg,
    currentUnitCostElement
) {
    let cartItemAsString = JSON.parse(sessionStorage.getItem(currentProductId));
    let currentQuantity = Number(cartItemAsString.quantity);
    const hasMinimumAllowedQuantity = isUpdatedQuantityOne(currentQuantity - 1);

    if (isUpdatedQuantityOne(currentQuantity)) {
        console.log(currentQuantity);
        updateTotal('neither', currentProductId);
    } else {
        console.log(currentQuantity);
        updateTotal('decrement', currentProductId);
    }

    currentQuantity = decrementQuantity(currentQuantity);
    cartItemAsString.quantity = currentQuantity;
    let updatedTotalCost = calculateAndSetProductTotalCost(
        cartItemAsString,
        currentQuantity,
        currentProductId
    );
    currentUnitCostElement.textContent = '£' + updatedTotalCost;
    currentQuantityElement.textContent = currentQuantity;

    decreaseQtyImg.className = hasMinimumAllowedQuantity
        ? 'cart-box-btn__disabled'
        : 'cart-box-btn__enabled';
}

function calculateAndSetProductTotalCost(cartItemAsString, currentQuantity, currentProductId) {
    let updatedTotalCost = Number(cartItemAsString.unitPrice) * currentQuantity;
    updatedTotalCost = updatedTotalCost.toFixed(2);
    cartItemAsString.quantity = currentQuantity;
    cartItemAsString.totalCost = updatedTotalCost;
    sessionStorage.setItem(currentProductId, JSON.stringify(cartItemAsString));
    return updatedTotalCost;
}

function addCartItem(addCartBtn, productList) {
    if (supportsTemplate) {
        let templateClone = createTemplate('#cart-box-template');
        let currentProductId = addCartBtn.getAttribute('data-product-id');
        let cartItem = {
            name: productList[currentProductId].productName,
            unitPrice: productList[currentProductId].productUnitPrice,
            quantity: 1,
            totalCost: productList[currentProductId].productUnitPrice,
        };

        if (sessionStorage.getItem(currentProductId) < 1) {
            sessionStorage.setItem(currentProductId, JSON.stringify(cartItem));
            appendProductTitle(templateClone, currentProductId, productList);

            appendProductPrice(templateClone, currentProductId, productList);

            const cartContent = document.querySelector('.cart-content');
            cartContent.appendChild(templateClone);

            let templateClone2 = cartContent.querySelectorAll('.cart-box');
            updateTotal('increment', currentProductId);
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

export { addCartItem };
