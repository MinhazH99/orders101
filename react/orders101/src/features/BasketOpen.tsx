type Props = {
  openCart: () => void;
};

function BasketOpen({ openCart }: Props) {
  return (
    <button onClick={() => openCart()} className="nav__icons" id="cart-icon">
      <div className="nav__icons__item container">
        <img src="./basket.svg" alt="basket"></img>
        <span>Basket</span>
      </div>
    </button>
  );
}

export default BasketOpen;
