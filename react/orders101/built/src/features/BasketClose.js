"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function BasketClose({ closeCart }) {
    return ((0, jsx_runtime_1.jsx)("button", { onClick: () => closeCart(), children: (0, jsx_runtime_1.jsx)("div", { children: (0, jsx_runtime_1.jsx)("img", { src: "./src/assets/x.svg" }) }) }));
}
exports.default = BasketClose;
