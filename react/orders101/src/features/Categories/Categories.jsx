import ListOfCategories from "./ListOfCategories";

function Categories() {
  return (
    <nav class="categories container nav-color ">
      <ul class="hamburger">
        <li>
          <button class="hamburger__btn-close" id="hide-side-bar">
            <img class="close" src="../src/assets/x.svg" alt=""></img>
          </button>
        </li>
        <ListOfCategories href={"#decoration"} text={"Decorations"} />
        <ListOfCategories href={"#garden"} text={"Garden"} />
        <ListOfCategories href={"#kitchen"} text={"Kitchen"} />
        <ListOfCategories href={"#tools"} text={"Tools"} />
        <ListOfCategories href={"#storage"} text={"Storage"} />
      </ul>
      <ul class="categories-list">
        <ListOfCategories href={"#decoration"} text={"Decorations"} />
        <ListOfCategories href={"#garden"} text={"Garden"} />
        <ListOfCategories href={"#kitchen"} text={"Kitchen"} />
        <ListOfCategories href={"#tools"} text={"Tools"} />
        <ListOfCategories href={"#storage"} text={"Storage"} />
      </ul>
    </nav>
  );
}

export default Categories;
