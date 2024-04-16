function BasketClose() {
  function handleClick() {
    document
      .querySelector("#cart-close")
      .addEventListener("click", function () {
        const cart = document.querySelector(".cart");
        cart.style.display = "none";
      });
  }
  return (
    <button onClick={handleClick}>
      <div>
        <img src="./src/assets/x.svg"></img>
      </div>
    </button>
  );
}

export default BasketClose;
