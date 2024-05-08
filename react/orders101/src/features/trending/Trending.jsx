import TrendingProduct from "./TrendingProduct";

function Trending() {
  return (
    <div>
      <h2 className="subtitle">Trending now</h2>
      <div id="trending" className="trending grid">
        <TrendingProduct />
      </div>
    </div>
  );
}

export default Trending;
