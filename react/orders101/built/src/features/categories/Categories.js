"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const ListOfCategories_1 = __importDefault(require("./ListOfCategories"));
const HamburgerClose_1 = __importDefault(require("../HamburgerClose"));
const categories = ["Decorations", "Garden", "Kitchen", "Tools", "Storage"];
const listCategories = categories.map((category) => ((0, jsx_runtime_1.jsx)(ListOfCategories_1.default, { href: "#" + category, text: category }, category)));
function Categories() {
    return ((0, jsx_runtime_1.jsxs)("nav", { className: "categories container nav-color ", children: [(0, jsx_runtime_1.jsxs)("ul", { className: "hamburger", children: [(0, jsx_runtime_1.jsx)("li", { children: (0, jsx_runtime_1.jsx)(HamburgerClose_1.default, {}) }), listCategories] }), (0, jsx_runtime_1.jsx)("ul", { className: "categories-list", children: listCategories })] }));
}
exports.default = Categories;
