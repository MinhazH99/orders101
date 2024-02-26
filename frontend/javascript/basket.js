function removeCartItem() {
    let cartRemove_btns = document.querySelectorAll('.cart-remove');

    cartRemove_btns.forEach((btn) => {
        btn.addEventListener('click', function () {
            btn.parentElement.remove();
            updateTotal();
        });
    });
}

removeCartItem();

function updateTotal() {
    let cartBoxes = document.querySelectorAll('.cart-box');
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

function changeQuantity() {
    let cartBoxes = document.querySelectorAll('.cart-box');
    let decreaseQtyImg = document.querySelector('.cart-box-btn__disabled ');
    cartBoxes.forEach((cartBox) => {
        let currentQuantityElement = document.querySelector('.cart-box__product-quantity');
        let currentQuantity = Number(
            document.querySelector('.cart-box__product-quantity').innerHTML
        );

        let increaseInQuantitybtn = document.querySelector('.cart-box_quantity-increase');
        increaseInQuantitybtn.addEventListener('click', function () {
            decreaseQtyImg.className = 'cart-box-btn__enabled';
            currentQuantity += 1;
            currentQuantityElement.innerHTML = currentQuantity;
            updateTotal();
        });

        let decreaseInQuantitybtn = document.querySelector('.cart-box_quantity-decrease');
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

changeQuantity();

window.onload(isUpdatedQuantityOne());

export { removeCartItem, updateTotal, changeQuantity, isUpdatedQuantityOne };
