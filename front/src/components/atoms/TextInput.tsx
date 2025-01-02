import React from "react";

type TextInputProps = {
  value: string;
  name: string;
  type?: "text" | "email" | "password" | "number" | "tel";
  label?: string;
  placeholder?: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
};

// TODO: 스타일 변경
const TextInput: React.FC<TextInputProps> = ({
  label,
  value,
  type = "text",
  onChange,
  placeholder,
  name,
}) => {
  const id = `input-${label || Math.random().toString(36).substring(2, 5)}`;
  return (
    <label htmlFor={id}>
      {label && <span className="">{label}</span>}
      <input
        id={id}
        name={name}
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className="bg-slate-200 rounded-md p-2 w-full"
      />
    </label>
  );
};

export default TextInput;
