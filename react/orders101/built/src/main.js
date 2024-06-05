"use strict";
var __importDefault = (this && this.__importDefault) || function (mod) {
    return (mod && mod.__esModule) ? mod : { "default": mod };
};
Object.defineProperty(exports, "__esModule", { value: true });
const jsx_runtime_1 = require("react/jsx-runtime");
const react_1 = __importDefault(require("react"));
const client_1 = __importDefault(require("react-dom/client"));
const App_jsx_1 = __importDefault(require("./App.jsx"));
const react_router_dom_1 = require("react-router-dom");
const Checkout_jsx_1 = __importDefault(require("./pages/Checkout.jsx"));
const router = (0, react_router_dom_1.createBrowserRouter)([
    {
        path: "/",
        element: (0, jsx_runtime_1.jsx)(App_jsx_1.default, {}),
    },
    {
        path: "/checkout",
        element: (0, jsx_runtime_1.jsx)(Checkout_jsx_1.default, {}),
    },
]);
client_1.default.createRoot(document.getElementById("root")).render((0, jsx_runtime_1.jsx)(react_1.default.StrictMode, { children: (0, jsx_runtime_1.jsx)(react_router_dom_1.RouterProvider, { router: router }) }));
