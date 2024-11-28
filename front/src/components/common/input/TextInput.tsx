import React from "react";

type TextInputPropsType = {
  lable?: string;
  value: string;
  type: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  placeholder?: string;
};

const TextInput: React.FC<TextInputPropsType> = ({
  lable,
  value,
  type,
  onChange,
  placeholder,
}) => {
  return (
    <div className="flex flex-col grow gap-1">
      <label className="text-base">{lable}</label>
      <input
        type={type}
        value={value}
        onChange={onChange}
        placeholder={placeholder}
        className="bg-slate-200 rounded-md p-2"
      />
    </div>
  );
};

export default TextInput;
