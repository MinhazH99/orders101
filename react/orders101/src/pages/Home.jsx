import Banner from "../components/Banner";
import Categories from "../components/Categories";
import Features from "../components/Features";
import Header from "../components/Header";
import Navbar from "../components/Navbar";
import Popular from "../components/Popular";

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
