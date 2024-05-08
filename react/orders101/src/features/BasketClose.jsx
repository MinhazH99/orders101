function BasketClose({ closeCart }) {
  return (
    <button onClick={() => closeCart()}>
      <div>
        <img src="./src/assets/x.svg"></img>
      </div>
    </button>
  );
}

export default BasketClose;
