import { useState, createContext, useEffect } from "react";
import "/src/css/orders101-ui.css";
import Home from "./pages/Home";
import Cart from "./features/cart/Cart";

export const ShoppingCartContext = createContext({
  cartItems: [],
  addToCart: () => {},
});

function App() {
  let [cartItems, setCartItems] = useState([]);

  const addToCart = (product) => {
    cartItems = [...cartItems, product];
    setCartItems(cartItems);
    console.log(cartItems);
    sessionStorage.setItem(product.id, JSON.stringify(product));
  };

  return (
    <ShoppingCartContext.Provider value={{ cartItems, addToCart }}>
      <Home />
      <Cart />
    </ShoppingCartContext.Provider>
  );
}

export default App;
