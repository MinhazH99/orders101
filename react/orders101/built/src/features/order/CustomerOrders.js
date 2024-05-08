"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function CustomerOrders({ itemName = "test", quantity = "x", price = "x" }) {
    return ((0, jsx_runtime_1.jsxs)("div", { className: "order-box", children: [(0, jsx_runtime_1.jsx)("img", { src: "../src/assets/trending-product.webp", alt: " ", className: "order-img" }), (0, jsx_runtime_1.jsxs)("div", { className: "order-box__detail", children: [(0, jsx_runtime_1.jsx)("div", { className: "order-box__name", children: itemName }), (0, jsx_runtime_1.jsxs)("div", { className: "order-box__quantity", children: ["Quantity: ", quantity] }), (0, jsx_runtime_1.jsxs)("div", { className: "order-box__price", children: ["Price: ", price] })] })] }));
}
exports.default = CustomerOrders;
