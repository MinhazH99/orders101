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
    cartBoxes.forEach((cartBox) => {
        let currentQuantityElement = document.querySelector('.cart-box__product-quantity');
        let currentQuantity = Number(
            document.querySelector('.cart-box__product-quantity').innerHTML
        );

        let increaseInQuantitybtn = document.querySelector('.cart-box_quantity-increase');
        increaseInQuantitybtn.addEventListener('click', function () {
            currentQuantity += 1;
            currentQuantityElement.innerHTML = currentQuantity;
            updateTotal();
        });

        let decreaseInQuantitybtn = document.querySelector('.cart-box_quantity-decrease');
        decreaseInQuantitybtn.addEventListener('click', function () {
            if (isQuantitylessThanOne(currentQuantity)) {
                decreaseInQuantitybtn.ariaDisabled = true;
            } else {
                currentQuantity -= 1;
                currentQuantityElement.innerHTML = currentQuantity;
                updateTotal();
            }
        });
    });
}
// save quantity in session storage

function isQuantitylessThanOne(quantity) {
    if (quantity == 1) {
        return true;
    }
    return false;
}

changeQuantity();

window.onload(isQuantitylessThanOne());

export { removeCartItem, updateTotal, changeQuantity, isQuantitylessThanOne };
