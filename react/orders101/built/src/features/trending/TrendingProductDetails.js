"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function TrendingProductDetails({ label = "Placeholder", price = "£XX.XX" }) {
    return ((0, jsx_runtime_1.jsxs)("div", { children: [(0, jsx_runtime_1.jsx)("div", { id: "trending-div-product-label", children: label }), (0, jsx_runtime_1.jsx)("div", { id: "trending-div-product-price", children: price })] }));
}
exports.default = TrendingProductDetails;
