type ButtonProps<
  E = React.MouseEvent<HTMLButtonElement> | React.FormEvent<HTMLButtonElement>
> = {
  children: React.ReactNode;
  icon?: React.ReactNode;
  onClick?: (e: E) => void;
  style?: string;
  disabled?: boolean;
  type?: "button" | "submit" | "reset";
};

// TODO: 스타일 변경
const Button: React.FC<ButtonProps> = ({
  children,
  icon,
  onClick,
  style,
  type,
  disabled = false,
}) => {
  return (
    <button
      className={`flex justify-center items-center gap-1 text-center px-3 py-2 rounded-md w-full font-bold ${style}`}
      onClick={onClick}
      type={type}
      disabled={disabled}>
      {icon}
      {children}
    </button>
  );
};

export default Button;
