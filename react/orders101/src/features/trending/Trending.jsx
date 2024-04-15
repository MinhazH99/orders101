import { useEffect, useState } from "react";
import TrendingImage from "./TrendingImage";
import TrendingProductDetails from "./TrendingProductDetails";

function Trending() {
  const [product, setProduct] = useState([]);

  useEffect(() => {
    fetch("http://localhost:8081/products/")
      .then((response) => {
        if (!response.ok) {
          throw new Error("Network response was not ok");
        }
        return response.json();
      })
      .then((json) => json.data)
      .then((json) => setProduct(json.data))
      .catch((err) => console.error("Error:", err));
  }),
    [];

  console.log(product);

  return (
    <div>
      <h2 class="subtitle">Trending now</h2>
      <div id="trending" class="trending grid">
        <div class="trending-div-product-grid">
          <a href="#popular">
            <div class="trending-div-product-image">
              <TrendingImage />
            </div>
            <TrendingProductDetails />
          </a>
          <button class="trending-div-product-addcart btn-buy">
            Add to Basket
          </button>
        </div>
      </div>
    </div>
  );
}

export default Trending;
