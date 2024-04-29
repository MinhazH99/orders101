import Banner from "../features/banner/Banner";
import Categories from "../features/categories/Categories";
import Features from "../features/usp/Features";
import Header from "../features/header/Header";
import Navbar from "../features/navbar/Navbar";
import Popular from "../features/popular/Popular";
import Trending from "../features/trending/Trending";

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
    </div>
  );
}

export default Home;
