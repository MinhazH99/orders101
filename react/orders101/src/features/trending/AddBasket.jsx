import { useContext, useState } from "react";
import { ShoppingCartContext } from "../../App";

function AddBasket({ id, name, unitPrice, quantity }) {
  const { addToCart } = useContext(ShoppingCartContext);
  const product = {
    id: id,
    name: name,
    unitPrice: unitPrice,
    quantity: quantity,
    totalCost: unitPrice,
  };

  return (
    <button
      onClick={() => addToCart(product)}
      class="trending-div-product-addcart btn-buy"
      data-product-id={id}
    >
      Add to Baskets
    </button>
  );
}

export default AddBasket;
