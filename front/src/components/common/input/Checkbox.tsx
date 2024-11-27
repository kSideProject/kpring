type CheckboxOptionType = {
  label: string;
  value: string;
  id: string;
};

type CheckboxPropsType = {
  options: CheckboxOptionType[];
  selectedValues: string[];
  onChange: (selectedValues: string[]) => void;
};

const Checkbox: React.FC<CheckboxPropsType> = ({
  options,
  selectedValues,
  onChange,
}) => {
  const handleCheckboxChange = (value: string) => {
    if (selectedValues.includes(value)) {
      onChange(selectedValues.filter((val) => val !== value));
    } else {
      onChange([...selectedValues, value]);
    }
  };
  return (
    <div>
      {options.map((option) => (
        <label key={option.label}>
          <input
            type="checkbox"
            value={option.value}
            onChange={() => handleCheckboxChange(option.value)}
            checked={selectedValues.includes(option.value)}
          />
        </label>
      ))}
    </div>
  );
};

export default Checkbox;
