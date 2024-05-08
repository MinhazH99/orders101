"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function Hamburger() {
    function handleClick() {
        document
            .querySelector("#show-side-bar")
            .addEventListener("click", function () {
            const sidebar = document.querySelector(".hamburger");
            sidebar.style.display = "flex";
        });
    }
    return ((0, jsx_runtime_1.jsx)("button", { onClick: handleClick, id: "show-side-bar", children: (0, jsx_runtime_1.jsx)("div", { className: "search-icons container", children: (0, jsx_runtime_1.jsx)("img", { src: "../src/assets/list.svg", alt: "basket" }) }) }));
}
exports.default = Hamburger;
