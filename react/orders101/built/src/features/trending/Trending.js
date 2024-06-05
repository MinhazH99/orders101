"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const TrendingProduct_1 = __importDefault(require("./TrendingProduct"));
function Trending() {
    return ((0, jsx_runtime_1.jsxs)("div", { children: [(0, jsx_runtime_1.jsx)("h2", { className: "subtitle", children: "Trending now" }), (0, jsx_runtime_1.jsx)("div", { id: "trending", className: "trending grid", children: (0, jsx_runtime_1.jsx)(TrendingProduct_1.default, {}) })] }));
}
exports.default = Trending;
