function TrendingImage({ src = "../src/assets/trending-product.webp" }) {
  return (
    <div className="trending-div-product-image">
      <img
        id="trending-div-product-image"
        className="site-image"
        src={src}
      ></img>
    </div>
  );
}

export default TrendingImage;
