import { useState, createContext, useEffect } from "react";
import "/src/css/orders101-ui.css";
import Home from "./pages/Home";

export const ShoppingCartContext = createContext({
  cartItems: [],
  addToCart: () => {},
  increaseQuantity: () => {},
  decreaseQuantity: () => {},
  removeItem: () => {},
  cartTotal: 0,
});

function App() {
  let [cartItems, setCartItems] = useState([]);
  let [cartTotal, setCartTotal] = useState(0);

  const addToCart = (product) => {
    if (cartItems.some((item) => item.id === product.id)) {
      increaseQuantity(product.id);
    } else {
      cartItems = [...cartItems, product];
      setCartItems(cartItems);
      const newCartTotal = cartTotal + product.unitPrice;
      setCartTotal(newCartTotal);
      console.log(cartItems);
      sessionStorage.setItem(product.id, JSON.stringify(product));
    }
  };

  const increaseQuantity = (id) => {
    setCartItems((currItems) => {
      return currItems.map((item) => {
        if (item.id === id) {
          const unitCost = item.unitPrice;
          const quantity = item.quantity + 1;
          const totalCost = item.unitPrice * quantity;

          updateTotalCost(cartTotal, unitCost, totalCost, "increment");

          return {
            ...item,
            quantity: quantity,
            totalCost: totalCost,
          };
        } else {
          return { ...item, quantity: item.quantity };
        }
      });
    });
    console.log(cartItems);
  };

  const decreaseQuantity = (id) => {
    setCartItems((currItems) => {
      return currItems.map((item) => {
        if (item.id === id && item.quantity > 1) {
          const unitCost = item.unitPrice;
          const quantity = item.quantity - 1;
          const totalCost = item.unitPrice * quantity;

          updateTotalCost(cartTotal, unitCost, totalCost, "decrement");

          return {
            ...item,
            quantity: quantity,
            totalCost: totalCost,
          };
        } else {
          return { ...item, quantity: item.quantity };
        }
      });
    });
  };

  const removeItem = (id) => {
    setCartItems((currItems) => {
      currItems.forEach((item) => {
        if (item.id == id) {
          const unitCost = item.unitPrice;
          const quantity = item.quantity;
          const totalCost = item.unitPrice * quantity;
          updateTotalCost(cartTotal, unitCost, totalCost, "delete");
        }
      });
      return currItems.filter((item) => item.id !== id);
    });
  };

  const updateTotalCost = (cartTotal, unitCost, totalCost, variation) => {
    if (variation == "increment") {
      const newCartTotal = cartTotal + unitCost;
      setCartTotal(newCartTotal);
    } else if (variation == "decrement") {
      const newCartTotal = cartTotal - unitCost;
      setCartTotal(newCartTotal);
    } else if (variation == "delete") {
      console.log(unitCost);
      console.log(totalCost);
      const newCartTotal = cartTotal - totalCost;
      setCartTotal(newCartTotal);
    }
  };

  return (
    <ShoppingCartContext.Provider
      value={{
        cartItems,
        cartTotal,
        addToCart,
        increaseQuantity,
        decreaseQuantity,
        removeItem,
      }}
    >
      <Home />
    </ShoppingCartContext.Provider>
  );
}

export default App;
