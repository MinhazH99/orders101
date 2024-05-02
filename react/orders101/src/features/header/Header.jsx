function Header() {
  return (
    <div className="account container">
      <ul className="account__sub-links">
        <li>
          <a href="#account">
            <img src="../src/assets/person-fill.svg"></img>
            <span>Account</span>
          </a>
        </li>
        <li>
          <a href="#contactus">
            <span>Contact Us</span>
          </a>
        </li>
      </ul>
    </div>
  );
}

export default Header;
