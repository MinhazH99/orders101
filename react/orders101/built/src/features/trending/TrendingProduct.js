"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const AddBasket_1 = __importDefault(require("./AddBasket"));
const TrendingImage_1 = __importDefault(require("./TrendingImage"));
const TrendingProductDetails_1 = __importDefault(require("./TrendingProductDetails"));
const react_1 = require("react");
function TrendingProduct() {
    const [products, setProducts] = (0, react_1.useState)([]);
    (0, react_1.useEffect)(() => {
        fetch("http://localhost:8081/products/")
            .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
            .then((json) => {
            setProducts(json.data);
        })
            .catch((err) => console.error("Error:", err));
    }, []);
    return products.map((product) => ((0, jsx_runtime_1.jsxs)("div", { className: "trending-div-product-grid", children: [(0, jsx_runtime_1.jsxs)("a", { href: "#popular", children: [(0, jsx_runtime_1.jsx)(TrendingImage_1.default, {}), (0, jsx_runtime_1.jsx)(TrendingProductDetails_1.default, { label: product.name, price: "£" + product.unitPrice.toFixed(2) })] }), (0, jsx_runtime_1.jsx)(AddBasket_1.default, { id: product.id, name: product.name, unitPrice: product.unitPrice, quantity: 1, totalCost: product.unitPrice })] }, product.id)));
}
exports.default = TrendingProduct;
