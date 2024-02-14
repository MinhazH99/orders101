function showSideBar() {
    console.log('in function');
    const sidebar = document.querySelector('.hamburger');
    sidebar.style.display = 'flex';
}

function hideSideBar() {
    const sidebar = document.querySelector('.hamburger');
    sidebar.style.display = 'none';
}

// Use this as anything defined in a module is scoped to that module, so use window to explicity expose those functions to global scope.
window.showSideBar = showSideBar;
window.hideSideBar = hideSideBar;

export { showSideBar, hideSideBar };
