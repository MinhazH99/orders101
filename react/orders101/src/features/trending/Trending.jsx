import TrendingProduct from "./TrendingProduct";

function Trending() {
  return (
    <div>
      <h2 class="subtitle">Trending now</h2>
      <div id="trending" class="trending grid">
        <TrendingProduct />
      </div>
    </div>
  );
}

export default Trending;
