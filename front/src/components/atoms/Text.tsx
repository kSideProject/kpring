import React from "react";

type TextProps = {
  children: React.ReactNode;
  styles: string;
};

const Text: React.FC<TextProps> = ({ children, styles }) => {
  return <p className={`${styles}`}>{children}</p>;
};

export default Text;
