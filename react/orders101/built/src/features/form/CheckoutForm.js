"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = require("react");
const checkoutService_1 = require("./checkoutService");
function CheckoutForm() {
    const customerFirstName = (0, react_1.useRef)(null);
    const customerSecondName = (0, react_1.useRef)(null);
    const customerEmailAddress = (0, react_1.useRef)(null);
    const deliveryCountry = (0, react_1.useRef)(null);
    const deliveryStreet = (0, react_1.useRef)(null);
    const deliveryPostCode = (0, react_1.useRef)(null);
    function handleClick() {
        (0, checkoutService_1.updateStock)();
        (0, checkoutService_1.submitOrder)({
            firstName: customerFirstName.current.value,
            secondName: customerSecondName.current.value,
            emailAddress: customerEmailAddress.current.value,
            streetName: deliveryStreet.current.value,
            postCode: deliveryPostCode.current.value,
            country: deliveryCountry.current.value,
        });
    }
    return ((0, jsx_runtime_1.jsxs)("div", { children: [(0, jsx_runtime_1.jsx)("div", { className: "checkout__child", children: (0, jsx_runtime_1.jsxs)("form", { className: "checkout__form", children: [(0, jsx_runtime_1.jsx)("span", { children: "1. Customer Details" }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "First Name" }) }), (0, jsx_runtime_1.jsx)("input", { className: "form__first-name", type: "text", ref: customerFirstName }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Second Name" }) }), (0, jsx_runtime_1.jsx)("input", { className: "form__second-name", type: "text", ref: customerSecondName }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Email Address" }) }), (0, jsx_runtime_1.jsx)("input", { className: "form__email-address", type: "text", ref: customerEmailAddress })] }) }), (0, jsx_runtime_1.jsx)("div", { className: "checkout__child", children: (0, jsx_runtime_1.jsxs)("form", { className: "checkout__form", children: [(0, jsx_runtime_1.jsx)("span", { children: "2. Delivery Address" }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Country Region" }) }), (0, jsx_runtime_1.jsx)("input", { className: "form__country", type: "text", ref: deliveryCountry }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Street Name" }) }), (0, jsx_runtime_1.jsx)("input", { className: "form__street-name", type: "text", ref: deliveryStreet }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Post Code" }) }), (0, jsx_runtime_1.jsx)("input", { className: "form__post-code", type: "text", ref: deliveryPostCode })] }) }), (0, jsx_runtime_1.jsx)("div", { className: "checkout__child", children: (0, jsx_runtime_1.jsxs)("form", { className: "checkout__form", children: [(0, jsx_runtime_1.jsx)("span", { children: "3. Payment Method" }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Card Number" }) }), (0, jsx_runtime_1.jsx)("input", { className: "text-field", type: "text" }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Cardholder Name" }) }), (0, jsx_runtime_1.jsx)("input", { className: "text-field", type: "text" }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Expiry Date" }) }), (0, jsx_runtime_1.jsx)("input", { className: "text-field", type: "text" }), (0, jsx_runtime_1.jsx)("label", { children: (0, jsx_runtime_1.jsx)("span", { children: "Security Code" }) }), (0, jsx_runtime_1.jsx)("input", { className: "text-field", type: "text" })] }) }), (0, jsx_runtime_1.jsxs)("div", { className: "checkout__terms-conditions", children: [(0, jsx_runtime_1.jsxs)("label", { children: [(0, jsx_runtime_1.jsx)("input", { type: "checkbox" }), (0, jsx_runtime_1.jsx)("span", { children: " I have read and accept the Terms and Conditions" })] }), (0, jsx_runtime_1.jsx)("button", { onClick: handleClick, className: "checkout__submit btn-buy", children: "Submit Order" })] })] }));
}
exports.default = CheckoutForm;
