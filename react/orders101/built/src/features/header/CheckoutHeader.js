"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const Header_1 = __importDefault(require("./Header"));
function CheckoutHeader() {
    return ((0, jsx_runtime_1.jsxs)("header", { children: [(0, jsx_runtime_1.jsx)(Header_1.default, {}), (0, jsx_runtime_1.jsx)("div", { className: "container", children: (0, jsx_runtime_1.jsx)("h1", { children: "DIYToolWorld" }) })] }));
}
exports.default = CheckoutHeader;
