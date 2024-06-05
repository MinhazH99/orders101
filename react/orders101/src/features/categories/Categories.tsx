import ListOfCategories from "./ListOfCategories";
import HamburgerClose from "../HamburgerClose";
import React from "react";

const categories = ["Decorations", "Garden", "Kitchen", "Tools", "Storage"];

const listCategories = categories.map((category) => (
  <ListOfCategories key={category} href={"#" + category} text={category} />
));

function Categories() {
  return (
    <nav className="categories container nav-color ">
      <ul className="hamburger">
        <li>
          <HamburgerClose />
        </li>
        {listCategories}
      </ul>
      <ul className="categories-list">{listCategories}</ul>
    </nav>
  );
}
export default Categories;
