import Message from "../atoms/Message";
import TextInput from "../atoms/TextInput";

type FormFieldProps = {
  value: string;
  name: string;
  type?: "text" | "email" | "password" | "number" | "tel";
  label?: string;
  placeholder?: string;
  onChange: (e: React.ChangeEvent<HTMLInputElement>) => void;
  message: string;
  style?: string;
};

const FormField: React.FC<FormFieldProps> = ({
  value,
  type,
  label,
  placeholder,
  onChange,
  message,
  name,
  style,
}) => {
  return (
    <div className="flex flex-col gap-1">
      <TextInput
        value={value}
        name={name}
        type={type}
        label={label}
        placeholder={placeholder}
        onChange={onChange}
        style={style}
      />
      <Message value={message} />
    </div>
  );
};

export default FormField;
