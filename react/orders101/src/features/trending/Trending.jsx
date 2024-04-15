import TrendingImage from "./TrendingImage";
import TrendingProductDetails from "./TrendingProductDetails";

function Trending() {
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
