"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const App_1 = require("../../App");
function AddBasket({ id, name, unitPrice, quantity, totalCost }) {
    const { addToCart } = (0, react_1.useContext)(App_1.ShoppingCartContext);
    const product = {
        id: id,
        name: name,
        unitPrice: unitPrice,
        quantity: quantity,
        totalCost: unitPrice,
    };
    return ((0, jsx_runtime_1.jsx)("button", { onClick: () => addToCart(product), className: "trending-div-product-addcart btn-buy", "data-product-id": id, children: "Add to Baskets" }));
}
exports.default = AddBasket;
