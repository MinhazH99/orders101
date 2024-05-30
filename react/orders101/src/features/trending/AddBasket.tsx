import { useContext } from "react";
import { ShoppingCartContext } from "../../App";
import { Product } from "../../Types";

type Props = {
  id: string;
  name: string;
  unitPrice: number;
  quantity: number;
};

function AddBasket({ id, name, unitPrice, quantity }: Props) {
  const { addToCart } = useContext(ShoppingCartContext);
  const product: Product = {
    id: id,
    name: name,
    unitPrice: unitPrice,
    quantity: quantity,
    totalCost: unitPrice,
  };

  return (
    <button
      onClick={() => addToCart(product)}
      className="trending-div-product-addcart btn-buy"
      data-product-id={id}
    >
      Add to Baskets
    </button>
  );
}

export default AddBasket;
