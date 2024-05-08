"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const CheckoutForm_1 = __importDefault(require("../features/form/CheckoutForm"));
const CheckoutHeader_1 = __importDefault(require("../features/header/CheckoutHeader"));
const OrderSummary_1 = __importDefault(require("../features/order/OrderSummary"));
function Checkout() {
    return ((0, jsx_runtime_1.jsxs)("div", { children: [(0, jsx_runtime_1.jsx)(CheckoutHeader_1.default, {}), (0, jsx_runtime_1.jsx)("div", { className: "container", children: (0, jsx_runtime_1.jsxs)("div", { className: "checkout grid", children: [(0, jsx_runtime_1.jsx)(CheckoutForm_1.default, {}), (0, jsx_runtime_1.jsx)(OrderSummary_1.default, {})] }) })] }));
}
exports.default = Checkout;
