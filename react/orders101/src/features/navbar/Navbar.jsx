import React, { useState } from "react";
import BasketOpen from "../BasketOpen";
import Hamburger from "../Hamburger";
import Cart from "../cart/Cart";

function Navbar() {
  let [isCartOpen, setIsCartOpen] = useState(false);

  const closeCart = () => {
    setIsCartOpen(false);
  };
  const openCart = () => {
    setIsCartOpen(true);
  };

  return (
    <React.Fragment>
      <div className="nav container">
        <div className="hamburger__btn-open">
          <Hamburger />
        </div>
        <a className="nav__icons" href="#stores">
          <div className="nav__icons__item container">
            <img src="../src/assets/geo-alt.svg" alt="geo locator"></img>
            <span>Stores</span>
          </div>
        </a>
        <div className="logo">
          <p>DIYToolWorld</p>
        </div>
        <a className="nav__icons" href="#ideas">
          <div className="nav__icons__item container">
            <img src="../src/assets/lightbulb.png" alt="lightbulb"></img>
            <span>Ideas & Advice</span>
          </div>
        </a>

        <BasketOpen openCart={openCart} />
        <div className="nav__search-container">
          <input className="search-bar" type="search" placeholder="Search..." />
        </div>
      </div>
      <Cart isOpen={isCartOpen} closeCart={closeCart} />
    </React.Fragment>
  );
}

export default Navbar;
