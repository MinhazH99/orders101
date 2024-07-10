"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function HamburgerClose() {
    function handleClick() {
        document
            .querySelector("#hide-side-bar")
            .addEventListener("click", function () {
            const sidebar = document.querySelector(".hamburger");
            if (sidebar) {
                sidebar.style.display = "none";
            }
        });
    }
    return ((0, jsx_runtime_1.jsx)("button", { onClick: handleClick, className: "hamburger__btn-close", id: "hide-side-bar", children: (0, jsx_runtime_1.jsx)("img", { className: "close", src: "/x.svg", alt: "" }) }));
}
exports.default = HamburgerClose;
