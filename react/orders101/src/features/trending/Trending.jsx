function Trending() {
  return (
    <div id="trending" class="trending grid">
      <template id="trending-product-template">
        <div class="trending-div-product-grid">
          <a href="#popular">
            <div class="trending-div-product-image">
              <img
                id="trending-div-product-image"
                class="site-image"
                src=""
              ></img>
            </div>
            <div id="trending-div-product-label">Placeholder</div>
            <div id="trending-div-product-price">£XX.XX</div>
          </a>
          <button class="trending-div-product-addcart btn-buy">
            Add to Basket
          </button>
        </div>
      </template>
    </div>
  );
}

export default Trending;
