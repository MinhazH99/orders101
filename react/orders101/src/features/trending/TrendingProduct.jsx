import AddBasket from "./AddBasket";
import TrendingImage from "./TrendingImage";
import TrendingProductDetails from "./TrendingProductDetails";
import { useEffect, useState } from "react";

function TrendingProduct() {
  const [products, setProducts] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8081/products/")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((json) => {
        setProducts(json.data);
      })
      .catch((err) => console.error("Error:", err));
  }, []);

  return products.map((product) => (
    <div class="trending-div-product-grid">
      <a href="#popular">
        <TrendingImage />
        <TrendingProductDetails
          label={product.name}
          price={"£" + product.unitPrice.toFixed(2)}
        />
      </a>
      <AddBasket
        id={product.id}
        name={product.name}
        unitPrice={product.unitPrice}
      />
    </div>
  ));
}

export default TrendingProduct;
