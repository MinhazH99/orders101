"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const App_1 = require("../../App");
function CartBox({ name = "Item Name", price = "£XX.XX", quantity = 1, id, }) {
    const { increaseQuantity, decreaseQuantity, removeItem } = (0, react_1.useContext)(App_1.ShoppingCartContext);
    return ((0, jsx_runtime_1.jsxs)("div", { className: "cart-box", children: [(0, jsx_runtime_1.jsx)("img", { src: "/trending-product.webp", alt: " ", className: "cart-img" }), (0, jsx_runtime_1.jsxs)("div", { className: "cart-box__detail", children: [(0, jsx_runtime_1.jsx)("div", { className: "cart-box__product-detail", children: name }), (0, jsx_runtime_1.jsx)("div", { className: "cart-box__product_price", children: price }), (0, jsx_runtime_1.jsxs)("div", { className: "cart-box_quantity-control", children: [(0, jsx_runtime_1.jsx)("button", { onClick: () => decreaseQuantity(String(id)), className: "cart-box_quantity-decrease", children: (0, jsx_runtime_1.jsx)("img", { className: "cart-box-btn__disabled", src: "/dash-square.svg", alt: "" }) }), (0, jsx_runtime_1.jsx)("div", { className: "cart-box__product-quantity", children: quantity }), (0, jsx_runtime_1.jsx)("button", { onClick: () => increaseQuantity(String(id)), className: "cart-box_quantity-increase", children: (0, jsx_runtime_1.jsx)("img", { src: "/plus-square.svg", alt: "" }) })] })] }), (0, jsx_runtime_1.jsx)("button", { onClick: () => removeItem(String(id)), className: "cart-remove", children: (0, jsx_runtime_1.jsx)("img", { src: "/trash3-fill.svg" }) })] }));
}
exports.default = CartBox;
