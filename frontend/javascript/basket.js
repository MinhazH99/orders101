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
        let priceElement = cartBox.querySelector('cart-box__product_price');
        let price = parseFloat(priceElement.innerHTML.replace('£', ''));
        let quantity = cartBox.querySelector('.cart-box__product-quantity').nodeValue;
        total += price * quantity;
    });

    total = total.toFixed(2);

    totalPrice.innerHTML = '£' + total;
}

function increaseInQuantity() {
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
        });
    });
}

increaseInQuantity();

export { removeCartItem, updateTotal, increaseInQuantity };
