"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function HeaderLinks({ href, imgSrc, span }) {
    return ((0, jsx_runtime_1.jsx)("li", { children: (0, jsx_runtime_1.jsxs)("a", { href: href, children: [(0, jsx_runtime_1.jsx)("img", { src: imgSrc }), (0, jsx_runtime_1.jsx)("span", { children: span })] }) }));
}
exports.default = HeaderLinks;
