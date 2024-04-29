function BasketOpen({ openCart }) {
  return (
    <button onClick={() => openCart()} class="nav__icons" id="cart-icon">
      <div class="nav__icons__item container">
        <img
          class="searchicons"
          src="./src/assets/basket.svg"
          alt="basket"
        ></img>
        <span>Basket</span>
      </div>
    </button>
  );
}

export default BasketOpen;
