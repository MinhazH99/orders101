import Banner from "../components/Banner";
import Categories from "../components/Categories";
import Features from "../components/Features";
import Header from "../components/Header";
import Navbar from "../components/Navbar";

function Home() {
  return (
    <div>
      <Header />
      <Navbar />
      <Categories />
      <Features />
      <Banner />
    </div>
  );
}

export default Home;
