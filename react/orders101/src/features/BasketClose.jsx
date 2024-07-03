function BasketClose({ closeCart }) {
  return (
    <button onClick={() => closeCart()}>
      <div>
        <img src="./x.svg"></img>
      </div>
    </button>
  );
}

export default BasketClose;
