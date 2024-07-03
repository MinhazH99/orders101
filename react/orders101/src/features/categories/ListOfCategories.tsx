import React from "react";

type Props = {
  href: string;
  text: string;
};

function ListOfCategories({ href, text }: Props) {
  return (
    <li>
      <a href={href}>{text}</a>
    </li>
  );
}

export default ListOfCategories;
