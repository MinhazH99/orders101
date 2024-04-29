import ListOfCategories from "./ListOfCategories";
import HamburgerClose from "../HamburgerClose";

const categories = ["Decorations", "Garden", "Kitchen", "Tools", "Storage"];

const listCategories = categories.map((category) => (
  <ListOfCategories key={category} href={"#" + category} text={category} />
));

function Categories() {
  return (
    <nav class="categories container nav-color ">
      <ul class="hamburger">
        <li>
          <HamburgerClose />
        </li>
        {listCategories}
      </ul>
      <ul class="categories-list">{listCategories}</ul>
    </nav>
  );
}
export default Categories;
