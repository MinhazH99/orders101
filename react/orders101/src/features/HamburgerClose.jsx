function HamburgerClose() {
  function handleClick() {
    document
      .querySelector("#hide-side-bar")
      .addEventListener("click", function () {
        const sidebar = document.querySelector(".hamburger");
        sidebar.style.display = "none";
      });
  }

  return (
    <button
      onClick={handleClick}
      className="hamburger__btn-close"
      id="hide-side-bar"
    >
      <img className="close" src="../src/assets/x.svg" alt=""></img>
    </button>
  );
}

export default HamburgerClose;
