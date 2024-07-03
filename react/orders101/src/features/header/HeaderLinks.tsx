type Props = {
  href: string;
  imgSrc: string;
  span: string;
};

function HeaderLinks({ href, imgSrc, span }: Props) {
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
