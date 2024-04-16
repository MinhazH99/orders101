function TrendingImage({ src = "../src/assets/trending-product.webp" }) {
  return (
    <div class="trending-div-product-image">
      <img id="trending-div-product-image" class="site-image" src={src}></img>
    </div>
  );
}

export default TrendingImage;
