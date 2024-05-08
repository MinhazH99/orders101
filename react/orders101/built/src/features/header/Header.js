"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const text_json_1 = __importDefault(require("../../text/text.json"));
const HeaderLinks_1 = __importDefault(require("./HeaderLinks"));
function Header() {
    let counter = 0;
    const headerLinks = text_json_1.default.map((data) => ((counter += 1),
        ((0, jsx_runtime_1.jsx)(HeaderLinks_1.default, { href: data.href, imgSrc: data.imgSrc, span: data.span }, counter))));
    return ((0, jsx_runtime_1.jsx)("div", { className: "account container", children: (0, jsx_runtime_1.jsx)("ul", { className: "account__sub-links", children: headerLinks }) }));
}
exports.default = Header;
