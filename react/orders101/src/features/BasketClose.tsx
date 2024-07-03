type Props = {
  closeCart: () => void;
};

function BasketClose({ closeCart }: Props) {
  return (
    <button onClick={() => closeCart()}>
      <div>
        <img src="./x.svg"></img>
      </div>
    </button>
  );
}

export default BasketClose;
