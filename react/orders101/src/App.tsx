import { useState, createContext } from "react";
import "/src/css/orders101-ui.css";
import Home from "./pages/Home";
import { Product } from "./Types";
import { ShoppingCartContextType } from "./Types";

export const ShoppingCartContext = createContext({} as ShoppingCartContextType);

function App() {
  let [cartItems, setCartItems] = useState<Product[]>([]);
  let [cartTotal, setCartTotal] = useState(0);

  const addToCart = (product: Product) => {
    if (cartItems.some((item) => item.id === product.id)) {
      console.log(cartItems);
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

  const increaseQuantity = (id: string) => {
    setCartItems((currItems) => {
      return currItems.map((item) => {
        if (item.id === id) {
          const unitCost = item.unitPrice;
          const quantity = item.quantity + 1;
          const totalCost = item.unitPrice * quantity;

          updateTotalCost(cartTotal, unitCost, totalCost, "increment");

          sessionStorage.setItem(id, JSON.stringify(item));

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

  const decreaseQuantity = (id: string) => {
    setCartItems((currItems) => {
      return currItems.map((item) => {
        if (item.id === id && item.quantity > 1) {
          const unitCost = item.unitPrice;
          const quantity = item.quantity - 1;
          const totalCost = item.unitPrice * quantity;

          updateTotalCost(cartTotal, unitCost, totalCost, "decrement");
          sessionStorage.setItem(id, JSON.stringify(item));

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

  const removeItem = (id: string) => {
    setCartItems((currItems) => {
      currItems.forEach((item) => {
        if (item.id == id) {
          const unitCost = item.unitPrice;
          const quantity = item.quantity;
          const totalCost = item.unitPrice * quantity;
          updateTotalCost(cartTotal, unitCost, totalCost, "delete");
          sessionStorage.removeItem(id);
        }
      });
      return currItems.filter((item) => item.id !== id);
    });
  };

  const updateTotalCost = (
    cartTotal: number,
    unitCost: number,
    totalCost: number,
    variation: string
  ) => {
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
