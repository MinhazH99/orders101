function attachEventListeners() {
    document.querySelector('#show-side-bar').addEventListener('click', function () {
        const sidebar = document.querySelector('.hamburger');
        sidebar.style.display = 'flex';
    });

    document.querySelector('#hide-side-bar').addEventListener('click', function () {
        const sidebar = document.querySelector('.hamburger');
        sidebar.style.display = 'none';
    });
}

attachEventListeners();

export { attachEventListeners };
