function removeCartItem(templateClone) {
    let cartRemove_btn = document.querySelector('.cart-content').querySelectorAll('.cart-remove');

    cartRemove_btn.forEach((btn) => {
        btn.addEventListener('click', function () {
            btn.parentElement.remove();
            updateTotal(templateClone);
        });
    });
}

function updateTotal(templateClone) {
    let cartBoxes = document.querySelector('.cart-content').querySelectorAll('.cart-box');
    let totalPrice = document.querySelector('.total__price');
    let total = parseFloat(totalPrice.innerHTML.replace('£', ''));

    cartBoxes.forEach((cartBox) => {
        let priceElement = cartBox.querySelector('.cart-box__product_price');
        let price = parseFloat(priceElement.innerHTML.replace('£', ''));
        let quantity = cartBox.querySelector('.cart-box__product-quantity').innerHTML;
        total += price * quantity;
    });

    total = total.toFixed(2);

    totalPrice.innerHTML = '£' + total;
}

function changeQuantity(templateClone) {
    let cartBoxes = templateClone.querySelectorAll('.cart-box');
    let decreaseQtyImg = templateClone.querySelector('.cart-box-btn__disabled ');
    cartBoxes.forEach((cartBox) => {
        let currentQuantityElement = templateClone.querySelector('.cart-box__product-quantity');
        let currentQuantity = Number(
            templateClone.querySelector('.cart-box__product-quantity').innerHTML
        );

        let increaseInQuantitybtn = templateClone.querySelector('.cart-box_quantity-increase');
        increaseInQuantitybtn.addEventListener('click', function () {
            decreaseQtyImg.className = 'cart-box-btn__enabled';
            currentQuantity += 1;
            currentQuantityElement.innerHTML = currentQuantity;
            updateTotal(templateClone);
        });

        let decreaseInQuantitybtn = templateClone.querySelector('.cart-box_quantity-decrease');
        decreaseInQuantitybtn.addEventListener('click', function () {
            if (isUpdatedQuantityOne(currentQuantity - 1)) {
                currentQuantity = decrementQuantity(currentQuantity);
                currentQuantityElement.innerHTML = currentQuantity;
                updateTotal(templateClone);
                decreaseQtyImg.className = 'cart-box-btn__disabled';
            } else {
                decreaseQtyImg.className = 'cart-box-btn__enabled';
                currentQuantity = decrementQuantity(currentQuantity);
                currentQuantityElement.innerHTML = currentQuantity;
                updateTotal(templateClone);
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
        let templateClone = document.querySelector('#cart-box-template').content.cloneNode(true);

        // append title
        let productTitle = templateClone.querySelector('.cart-box__product-detail');
        productTitle.textContent = productList[addCartBtn.getAttribute('data-test')].productName;

        let productPriceElement = templateClone.querySelector('.cart-box__product_price');

        // convert price in to GBP with 2 d/p

        productPriceElement.textContent =
            '£' + productList[addCartBtn.getAttribute('data-test')].productUnitPrice.toFixed(2);

        const cartContent = document.querySelector('.cart-content');
        cartContent.appendChild(templateClone);

        updateTotal(templateClone);
        removeCartItem(templateClone);
        changeQuantity(templateClone);
    }
}

export { removeCartItem, updateTotal, changeQuantity, isUpdatedQuantityOne, addCartItem };
