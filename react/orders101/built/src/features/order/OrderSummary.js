"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const CustomerOrders_1 = __importDefault(require("./CustomerOrders"));
function OrderSummary() {
    let storage = [];
    let total = 0;
    Object.keys(sessionStorage).forEach((key) => {
        const order = JSON.parse(sessionStorage.getItem(key));
        storage.push(order);
        total += order.totalCost;
    });
    const listOfOrders = storage.map((order) => ((0, jsx_runtime_1.jsx)(CustomerOrders_1.default, { itemName: order.name, quantity: order.quantity, price: "£" + order.totalCost.toFixed(2) }, order.id)));
    return ((0, jsx_runtime_1.jsxs)("div", { className: "checkout__child", children: ["Order Details", (0, jsx_runtime_1.jsxs)("div", { className: "checkout__child__header", children: [(0, jsx_runtime_1.jsx)("span", { children: "Order Summary" }), (0, jsx_runtime_1.jsxs)("span", { className: "checkout_total-quantity", children: [" ", sessionStorage.length, " items"] })] }), (0, jsx_runtime_1.jsx)("div", { className: "checkout__order-number", children: (0, jsx_runtime_1.jsx)("span", { children: "Order Number:" }) }), (0, jsx_runtime_1.jsx)("div", { className: "order-details", children: listOfOrders }), (0, jsx_runtime_1.jsxs)("div", { className: "checkout__total-detail", children: [(0, jsx_runtime_1.jsx)("span", { className: "checkout__total-text", children: "Total" }), (0, jsx_runtime_1.jsx)("span", { className: "checkout__total-price", children: "£" + total.toFixed(2) })] })] }));
}
exports.default = OrderSummary;
