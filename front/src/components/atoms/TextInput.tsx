import React from "react";

type TextInputProps = {
  value: string;
  name: string;
  type?: "text" | "email" | "password" | "number" | "tel";
  label?: string;
  placeholder?: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  style?: string;
};

// TODO: 스타일 변경
const TextInput: React.FC<TextInputProps> = ({
  label,
  value,
  type = "text",
  onChange,
  placeholder,
  name,
  style,
}) => {
  const id = `input-${label || Math.random().toString(36).substring(2, 5)}`;
  return (
    <label htmlFor={id} className={`text-bold ${style}`}>
      {label && <span>{label}</span>}
      <input
        id={id}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className="text-black bg-white rounded-md p-2 w-full focus:border-tertiary focus:border-2 focus:outline-none"
      />
    </label>
  );
};

export default TextInput;
