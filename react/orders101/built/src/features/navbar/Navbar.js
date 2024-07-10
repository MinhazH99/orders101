"use strict";
var __createBinding = (this && this.__createBinding) || (Object.create ? (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    var desc = Object.getOwnPropertyDescriptor(m, k);
    if (!desc || ("get" in desc ? !m.__esModule : desc.writable || desc.configurable)) {
      desc = { enumerable: true, get: function() { return m[k]; } };
    }
    Object.defineProperty(o, k2, desc);
}) : (function(o, m, k, k2) {
    if (k2 === undefined) k2 = k;
    o[k2] = m[k];
}));
var __setModuleDefault = (this && this.__setModuleDefault) || (Object.create ? (function(o, v) {
    Object.defineProperty(o, "default", { enumerable: true, value: v });
}) : function(o, v) {
    o["default"] = v;
});
var __importStar = (this && this.__importStar) || function (mod) {
    if (mod && mod.__esModule) return mod;
    var result = {};
    if (mod != null) for (var k in mod) if (k !== "default" && Object.prototype.hasOwnProperty.call(mod, k)) __createBinding(result, mod, k);
    __setModuleDefault(result, mod);
    return result;
};
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = __importStar(require("react"));
const BasketOpen_1 = __importDefault(require("../BasketOpen"));
const Hamburger_1 = __importDefault(require("../Hamburger"));
const Cart_1 = __importDefault(require("../cart/Cart"));
function Navbar() {
    let [isCartOpen, setIsCartOpen] = (0, react_1.useState)(false);
    const closeCart = () => {
        setIsCartOpen(false);
    };
    const openCart = () => {
        setIsCartOpen(true);
    };
    return ((0, jsx_runtime_1.jsxs)(react_1.default.Fragment, { children: [(0, jsx_runtime_1.jsxs)("div", { className: "nav container", children: [(0, jsx_runtime_1.jsx)("div", { className: "hamburger__btn-open", children: (0, jsx_runtime_1.jsx)(Hamburger_1.default, {}) }), (0, jsx_runtime_1.jsx)("a", { className: "nav__icons", href: "#stores", children: (0, jsx_runtime_1.jsxs)("div", { className: "nav__icons__item container", children: [(0, jsx_runtime_1.jsx)("img", { src: "/geo-alt.svg", alt: "geo locator" }), (0, jsx_runtime_1.jsx)("span", { children: "Stores" })] }) }), (0, jsx_runtime_1.jsx)("div", { className: "logo", children: (0, jsx_runtime_1.jsx)("p", { children: "DIYToolWorld" }) }), (0, jsx_runtime_1.jsx)("a", { className: "nav__icons", href: "#ideas", children: (0, jsx_runtime_1.jsxs)("div", { className: "nav__icons__item container", children: [(0, jsx_runtime_1.jsx)("img", { src: "/lightbulb.svg", alt: "lightbulb" }), (0, jsx_runtime_1.jsx)("span", { children: "Ideas & Advice" })] }) }), (0, jsx_runtime_1.jsx)(BasketOpen_1.default, { openCart: openCart }), (0, jsx_runtime_1.jsx)("div", { className: "nav__search-container", children: (0, jsx_runtime_1.jsx)("input", { className: "search-bar", type: "search", placeholder: "Search..." }) })] }), (0, jsx_runtime_1.jsx)(Cart_1.default, { isOpen: isCartOpen, closeCart: closeCart })] }));
}
exports.default = Navbar;
