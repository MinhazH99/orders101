function attachEventListeners() {
    document.querySelector('#show-side-bar').addEventListener('click', function () {
        const sidebar = document.querySelector('.hamburger');
        sidebar.style.display = 'flex';
    });

    document.querySelector('#hide-side-bar').addEventListener('click', function () {
        const sidebar = document.querySelector('.hamburger');
        sidebar.style.display = 'none';
    });

    document.querySelector('#cart-icon').addEventListener('click', function () {
        const cart = document.querySelector('.cart');
        cart.style.display = 'block';
    });

    document.querySelector('#cart-close').addEventListener('click', function () {
        const cart = document.querySelector('.cart');
        cart.style.display = 'none';
    });
}

attachEventListeners();

export { attachEventListeners };
