import { useState, createContext, useEffect } from "react";
import "/src/css/orders101-ui.css";
import Home from "./pages/Home";
import Cart from "./features/cart/Cart";

export const ShoppingCartContext = createContext({
  cartItems: [],
  addToCart: () => {},
  increaseQuantity: () => {},
  decreaseQuantity: () => {},
  removeItem: () => {},
});

function App() {
  let [cartItems, setCartItems] = useState([]);

  const addToCart = (product) => {
    cartItems = [...cartItems, product];
    setCartItems(cartItems);
    console.log(cartItems);
    sessionStorage.setItem(product.id, JSON.stringify(product));
  };

  const increaseQuantity = (id) => {
    setCartItems((currItems) => {
      return currItems.map((item) => {
        if (item.id === id) {
          return { ...item, quantity: item.quantity + 1 };
        } else {
          return { ...item, quantity: item.quantity };
        }
      });
    });
  };

  const decreaseQuantity = (id) => {
    setCartItems((currItems) => {
      return currItems.map((item) => {
        if (item.id === id && item.quantity > 1) {
          return { ...item, quantity: item.quantity - 1 };
        } else {
          return { ...item, quantity: item.quantity };
        }
      });
    });
  };

  return (
    <ShoppingCartContext.Provider
      value={{ cartItems, addToCart, increaseQuantity, decreaseQuantity }}
    >
      <Home />
      <Cart />
    </ShoppingCartContext.Provider>
  );
}

export default App;
