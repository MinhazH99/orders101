"use strict";
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
function Banner() {
    return ((0, jsx_runtime_1.jsx)("div", { className: "container", children: (0, jsx_runtime_1.jsx)("img", { className: "banner-image", srcSet: "../src/assets/banner3-360w.jpg 360w, \n        ../src/assets/banner2-1084w.jpg 1084w, \n        ../src/assets/banner-1400w.webp 1400w", src: "../src/assets//banner3-360w.jpg", alt: "banner image" }) }));
}
exports.default = Banner;
