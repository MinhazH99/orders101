import BasketOpen from "../BasketOpen";
import Hamburger from "../Hamburger";

function Navbar() {
  return (
    <div class="nav container">
      <div class="hamburger__btn-open">
        <Hamburger />
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

      <BasketOpen />
      <div class="nav__search-container">
        <input class="search-bar" type="search" placeholder="Search..." />
      </div>
    </div>
  );
}

export default Navbar;
