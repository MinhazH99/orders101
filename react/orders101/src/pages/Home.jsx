import Banner from "../features/Banner";
import Categories from "../features/Categories/Categories";
import Features from "../features/Features";
import Header from "../features/Header";
import Navbar from "../features/Navbar";
import Popular from "../features/Popular";

function Home() {
  return (
    <div>
      <Header />
      <Navbar />
      <Categories />
      <Features />
      <Banner />
      <Popular />
    </div>
  );
}

export default Home;
