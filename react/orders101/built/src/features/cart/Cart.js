"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const BasketClose_1 = __importDefault(require("../BasketClose"));
const CartBox_1 = __importDefault(require("./CartBox"));
const Checkout_1 = __importDefault(require("./Checkout"));
const App_1 = require("../../App");
function Cart({ isOpen, closeCart }) {
    const { cartItems, cartTotal } = (0, react_1.useContext)(App_1.ShoppingCartContext);
    return ((0, jsx_runtime_1.jsxs)("div", { className: "cart", style: { display: isOpen ? "block" : "none" }, children: [(0, jsx_runtime_1.jsx)("h2", { className: "card-title", children: "Your Cart" }), (0, jsx_runtime_1.jsx)("div", { className: "cart-content", children: cartItems.map((cartItem) => ((0, jsx_runtime_1.jsx)(CartBox_1.default, { id: cartItem.id, name: cartItem.name, price: "£" + cartItem.totalCost.toFixed(2), quantity: cartItem.quantity }, cartItem.id))) }), (0, jsx_runtime_1.jsxs)("div", { className: "total", children: [(0, jsx_runtime_1.jsx)("div", { className: "total__title", children: (0, jsx_runtime_1.jsx)("h3", { children: "Total" }) }), (0, jsx_runtime_1.jsx)("div", { className: "total__price", children: "£" + cartTotal.toFixed(2) })] }), (0, jsx_runtime_1.jsx)(Checkout_1.default, {}), (0, jsx_runtime_1.jsx)("div", { id: "cart-close", children: (0, jsx_runtime_1.jsx)(BasketClose_1.default, { closeCart: closeCart }) })] }));
}
exports.default = Cart;
