function BasketClose() {
  function handleClick() {
    const cart = document.querySelector(".cart");
    cart.style.display = "none";
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
