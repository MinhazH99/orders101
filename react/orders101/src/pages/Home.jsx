import Banner from "../features/banner/Banner";
import Categories from "../features/categories/Categories";
import Features from "../features/usp/Features";
import Header from "../features/header/Header";
import Navbar from "../features/navbar/Navbar";
import Popular from "../features/popular/Popular";
import Trending from "../features/trending/Trending";
import Cart from "../features/cart/Cart";

function Home() {
  return (
    <div>
      <Header />
      <Navbar />
      <Categories />
      <Features />
      <Banner />
      <Popular />
      <Trending />
      <Cart />
    </div>
  );
}

export default Home;
