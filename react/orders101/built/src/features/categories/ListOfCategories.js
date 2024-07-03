"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function ListOfCategories({ href, text }) {
    return ((0, jsx_runtime_1.jsx)("li", { children: (0, jsx_runtime_1.jsx)("a", { href: href, children: text }) }));
}
exports.default = ListOfCategories;
