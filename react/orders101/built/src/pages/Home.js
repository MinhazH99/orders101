"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const Banner_1 = __importDefault(require("../features/banner/Banner"));
const Categories_1 = __importDefault(require("../features/categories/Categories"));
const Features_1 = __importDefault(require("../features/usp/Features"));
const Header_1 = __importDefault(require("../features/header/Header"));
const Navbar_1 = __importDefault(require("../features/navbar/Navbar"));
const Popular_1 = __importDefault(require("../features/popular/Popular"));
const Trending_1 = __importDefault(require("../features/trending/Trending"));
function Home() {
    return ((0, jsx_runtime_1.jsxs)("div", { children: [(0, jsx_runtime_1.jsx)(Header_1.default, {}), (0, jsx_runtime_1.jsx)(Navbar_1.default, {}), (0, jsx_runtime_1.jsx)(Categories_1.default, {}), (0, jsx_runtime_1.jsx)(Features_1.default, {}), (0, jsx_runtime_1.jsx)(Banner_1.default, {}), (0, jsx_runtime_1.jsx)(Popular_1.default, {}), (0, jsx_runtime_1.jsx)(Trending_1.default, {})] }));
}
exports.default = Home;
