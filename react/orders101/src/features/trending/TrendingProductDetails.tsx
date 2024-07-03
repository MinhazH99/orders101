function TrendingProductDetails({ label = "Placeholder", price = "£XX.XX" }) {
  return (
    <div>
      <div id="trending-div-product-label">{label}</div>
      <div id="trending-div-product-price">{price}</div>
    </div>
  );
}

export default TrendingProductDetails;
