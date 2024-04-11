function Navbar() {
  return (
    <div class="nav container">
      <div class="hamburger__btn-open">
        <button id="show-side-bar">
          <div class="search-icons container">
            <img
              class="searchicons"
              src="../src/assets/list.svg"
              alt="basket"
            ></img>
          </div>
        </button>
      </div>
      <a class="nav__icons" href="#stores">
        <div class="nav__icons__item container">
          <img
            class="searchicons"
            src="../src/assets/geo-alt.svg"
            alt="geo locator"
          ></img>
          <span>Stores</span>
        </div>
      </a>
      <div class="logo">
        <p>DIYToolWorld</p>
      </div>
      <a class="nav__icons" href="#ideas">
        <div class="nav__icons__item container">
          <img
            class="searchicons"
            src="../src/assets/lightbulb.svg"
            alt="lightbulb"
          ></img>
          <span>Ideas & Advice</span>
        </div>
      </a>

      <button class="nav__icons" id="cart-icon">
        <div class="nav__icons__item container">
          {" "}
          <img
            class="searchicons"
            src="../src/assets/basket.svg"
            alt="basket"
          ></img>
          <span>Basket</span>
        </div>
      </button>
      <div class="nav__search-container">
        <input class="search-bar" type="search" placeholder="Search..." />
      </div>
    </div>
  );
}

export default Navbar;
