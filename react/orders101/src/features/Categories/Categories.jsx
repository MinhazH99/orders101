function Categories() {
  return (
    <nav class="categories container nav-color ">
      <ul class="hamburger">
        <li>
          <button class="hamburger__btn-close" id="hide-side-bar">
            <img class="close" src="../src/assets/x.svg" alt=""></img>
          </button>
        </li>
        <li>
          <a href="#decorations">Decorations</a>
        </li>
        <li>
          <a href="#garden">Garden</a>
        </li>
        <li>
          <a href="#kitchen">Kitchen</a>
        </li>
        <li>
          <a href="#tools">Tools</a>
        </li>
        <li>
          <a href="#storage">Storage</a>
        </li>
      </ul>
      <ul class="categories-list">
        <li>
          <a href="#decorations">Decorations</a>
        </li>
        <li>
          <a href="#garden">Garden</a>
        </li>
        <li>
          <a href="#kitchen">Kitchen</a>
        </li>
        <li>
          <a href="#tools">Tools</a>
        </li>
        <li>
          <a href="#storage">Storage</a>
        </li>
      </ul>
    </nav>
  );
}

// Make list a component - pass href as object into component

export default Categories;
