import React from "react";

type RadioButtonProps = {
  options: React.ReactNode;
  name: string;
  value: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  checked: boolean | undefined;
  height?: string;
};

const RadioButton: React.FC<RadioButtonProps> = ({
  options,
  name,
  value,
  onChange,
  checked,
  height = "h-12",
}) => {
  const id = `radio-${name}-${value}`;

  return (
    <div className="flex-1 h-full items-center">
      <input
        type="radio"
        id={id}
        name={name}
        value={value}
        onChange={onChange}
        checked={checked}
        className="hidden"
      />
      <label
        htmlFor={id}
        className={`flex items-center justify-center gap-2 px-4 py-2 min-w-full border rounded-md cursor-pointer transition ${
          checked ? "bg-blue-300" : "bg-gray-400"
        } ${height}`}>
        {options}
      </label>
    </div>
  );
};

export default RadioButton;
