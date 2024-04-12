import ListOfCategories from "./ListOfCategories";

const categories = ["Decorations", "Garden", "Kitchen", "Tools", "Storage"];

const listCategories = categories.map((category) => (
  <ListOfCategories href={"#" + category} text={category} />
));

function Categories() {
  return (
    <nav class="categories container nav-color ">
      <ul class="hamburger">
        <li>
          <button class="hamburger__btn-close" id="hide-side-bar">
            <img class="close" src="../src/assets/x.svg" alt=""></img>
          </button>
        </li>
        {listCategories}
      </ul>
      <ul class="categories-list">{listCategories}</ul>
    </nav>
  );
}
export default Categories;
