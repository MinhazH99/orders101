"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function BasketOpen({ openCart }) {
    return ((0, jsx_runtime_1.jsx)("button", { onClick: () => openCart(), className: "nav__icons", id: "cart-icon", children: (0, jsx_runtime_1.jsxs)("div", { className: "nav__icons__item container", children: [(0, jsx_runtime_1.jsx)("img", { src: "./src/assets/basket.svg", alt: "basket" }), (0, jsx_runtime_1.jsx)("span", { children: "Basket" })] }) }));
}
exports.default = BasketOpen;
