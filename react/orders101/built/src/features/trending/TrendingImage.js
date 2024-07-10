"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function TrendingImage({ src = "/trending-product.webp" }) {
    return ((0, jsx_runtime_1.jsx)("div", { className: "trending-div-product-image", children: (0, jsx_runtime_1.jsx)("img", { id: "trending-div-product-image", className: "site-image", src: src }) }));
}
exports.default = TrendingImage;
