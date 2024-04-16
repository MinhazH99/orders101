function BasketOpen() {
  function handleClick() {
    document.querySelector("#cart-icon").addEventListener("click", function () {
      const cart = document.querySelector(".cart");
      cart.style.display = "block";
    });
  }

  return (
    <button onClick={handleClick} class="nav__icons" id="cart-icon">
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
