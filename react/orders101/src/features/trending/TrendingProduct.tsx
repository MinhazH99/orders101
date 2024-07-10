import { Product } from "../../Types";
import AddBasket from "./AddBasket";
import TrendingImage from "./TrendingImage";
import TrendingProductDetails from "./TrendingProductDetails";
import { useEffect, useState } from "react";

function TrendingProduct() {
  const [products, setProducts] = useState<Product[]>([]);

  useEffect(() => {
    fetch("https://18.169.70.179:9443/products")
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
    <div className="trending-div-product-grid" key={product.id}>
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
        quantity={1}
        totalCost={product.unitPrice}
      />
    </div>
  ));
}

export default TrendingProduct;
