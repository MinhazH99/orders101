import headerData from "../../text/text.json";
import HeaderLinks from "./HeaderLinks";

function Header() {
  let data = [];
  let counter = 0;
  Object.keys(headerData).forEach(function (key) {
    data.push(headerData[key]);
  });
  const headerLinks = data.map(
    (data) => (
      (counter += 1),
      (
        <HeaderLinks
          key={counter}
          href={data.href}
          imgSrc={data.imgSrc}
          span={data.span}
        />
      )
    )
  );
  return (
    <div className="account container">
      <ul className="account__sub-links">{headerLinks}</ul>
    </div>
  );
}

export default Header;
