type Props = {
  closeCart: () => void;
};

function BasketClose({ closeCart }: Props) {
  return (
    <button onClick={() => closeCart()}>
      <div>
        <img src="./src/assets/x.svg"></img>
      </div>
    </button>
  );
}

export default BasketClose;
