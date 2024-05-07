function HeaderLinks({ href, imgSrc, span }) {
  return (
    <li>
      <a href={href}>
        <img src={imgSrc}></img>
        <span>{span}</span>
      </a>
    </li>
  );
}

export default HeaderLinks;
