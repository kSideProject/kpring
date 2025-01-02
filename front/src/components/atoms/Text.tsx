import React from "react";

type TextProps = {
  children: React.ReactNode;
  color: string;
  size: string;
};

const Text: React.FC<TextProps> = ({ children, color, size }) => {
  return <p className={`${color} ${size}`}>{children}</p>;
};

export default Text;
