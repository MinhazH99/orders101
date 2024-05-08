"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_router_dom_1 = require("react-router-dom");
function Checkout() {
    return ((0, jsx_runtime_1.jsx)("button", { type: "button", className: "btn-buy", children: (0, jsx_runtime_1.jsx)(react_router_dom_1.Link, { to: "/checkout", children: "Checkout" }) }));
}
exports.default = Checkout;
