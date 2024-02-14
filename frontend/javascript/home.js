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
// Use this as anything defined in a module is scoped to that module, so use window to explicity expose those functions to global scope.

export { attachEventListeners };
