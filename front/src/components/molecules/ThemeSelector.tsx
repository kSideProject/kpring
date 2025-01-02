import React from "react";
import RadioButton from "../atoms/RadioButton";
import Text from "../atoms/Text";

const themeObj = [
  {
    id: "SERVER_THEME_001",
    name: "숲",
  },
  {
    id: "SERVER_THEME_002",
    name: "오피스",
  },
];

type ThemeSelectorProps = {
  onChange: (themeId: string) => void;
  selectedTheme: string;
};

const ThemeSelector: React.FC<ThemeSelectorProps> = ({
  onChange,
  selectedTheme,
}) => {
  return (
    <div className="flex flex-col">
      <Text color="" size="">
        테마
      </Text>
      <div className="flex flex-row justify-center items-center gap-3">
        {themeObj.map((theme) => (
          <RadioButton
            key={theme.id}
            options={theme.name}
            name="theme"
            value={theme.id}
            onChange={(e) => onChange(e.target.value)}
            checked={selectedTheme === theme.id}
            height="h-32"
          />
        ))}
      </div>
    </div>
  );
};

export default ThemeSelector;
