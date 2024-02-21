function removeCartItem() {
    let cardRemove_btns = document.querySelectorAll('.cart-remove');
    console.log(cardRemove_btns);

    cardRemove_btns.forEach((btn) => {
        btn.addEventListener('click', function () {
            btn.parentElement.remove();
        });
    });
}

removeCartItem();

export { removeCartItem };
