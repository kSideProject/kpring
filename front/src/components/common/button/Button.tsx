import { ReactNode } from "react";

type ButtonPropsType = {
  children: ReactNode;
  color: string;
  onClick: () => void;
};

const Button = ({ children, color, onClick }: ButtonPropsType) => {
  return (
    <button className={`p-3 ${color}`} onClick={onClick}>
      {children}
    </button>
  );
};

export default Button;
