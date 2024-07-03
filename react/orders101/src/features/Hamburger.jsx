function Hamburger() {
  function handleClick() {
    document
      .querySelector("#show-side-bar")
      .addEventListener("click", function () {
        const sidebar = document.querySelector(".hamburger");
        sidebar.style.display = "flex";
      });
  }
  return (
    <button onClick={handleClick} id="show-side-bar">
      <div className="search-icons container">
        <img className="searchicons" src="/list.svg" alt="basket"></img>
      </div>
    </button>
  );
}

export default Hamburger;
