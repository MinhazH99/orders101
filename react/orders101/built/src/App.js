"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
exports.ShoppingCartContext = void 0;
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
require("/src/css/orders101-ui.css");
const Home_1 = __importDefault(require("./pages/Home"));
exports.ShoppingCartContext = (0, react_1.createContext)({
    cartItems: [],
    addToCart: () => { },
    increaseQuantity: () => { },
    decreaseQuantity: () => { },
    removeItem: () => { },
    cartTotal: 0,
});
function App() {
    let [cartItems, setCartItems] = (0, react_1.useState)([]);
    let [cartTotal, setCartTotal] = (0, react_1.useState)(0);
    const addToCart = (product) => {
        if (cartItems.some((item) => item.id === product.id)) {
            increaseQuantity(product.id);
        }
        else {
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
                    return Object.assign(Object.assign({}, item), { quantity: quantity, totalCost: totalCost });
                }
                else {
                    return Object.assign(Object.assign({}, item), { quantity: item.quantity });
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
                    return Object.assign(Object.assign({}, item), { quantity: quantity, totalCost: totalCost });
                }
                else {
                    return Object.assign(Object.assign({}, item), { quantity: item.quantity });
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
        }
        else if (variation == "decrement") {
            const newCartTotal = cartTotal - unitCost;
            setCartTotal(newCartTotal);
        }
        else if (variation == "delete") {
            console.log(unitCost);
            console.log(totalCost);
            const newCartTotal = cartTotal - totalCost;
            setCartTotal(newCartTotal);
        }
    };
    return ((0, jsx_runtime_1.jsx)(exports.ShoppingCartContext.Provider, { value: {
            cartItems,
            cartTotal,
            addToCart,
            increaseQuantity,
            decreaseQuantity,
            removeItem,
        }, children: (0, jsx_runtime_1.jsx)(Home_1.default, {}) }));
}
exports.default = App;
