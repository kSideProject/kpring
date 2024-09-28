import React from "react";
import { useThemeStore } from "../../store/useThemeStore";
import { FormControlLabel, Radio, RadioGroup } from "@mui/material";
import { ThemeType } from "../../types/server";

const ServerThemeSelector = () => {
  const setTheme = useThemeStore((state) => state.setTheme);

  const handleThemeChange = (e: React.ChangeEvent<HTMLInputElement>) => {
    const themeId = e.target.id;

    let selectedTheme: ThemeType | undefined;

    console.log(selectedTheme);

    if (themeId === "SERVER_THEME_001") {
      selectedTheme = { id: "SERVER_THEME_001", name: "숲" };
    } else if (themeId === "SERVER_THEME_002") {
      selectedTheme = { id: "SERVER_THEME_002", name: "오피스" };
    }

    if (selectedTheme) {
      setTheme(selectedTheme);
    } else {
      console.log("theme id가 없어요");
    }
  };

  return (
    <RadioGroup row>
      <FormControlLabel
        labelPlacement="top"
        value="숲"
        control={<Radio id="SERVER_THEME_001" onChange={handleThemeChange} />}
        label={
          <img
            src="https://images.unsplash.com/photo-1718889339117-d90a40b1250b?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            width={100}
            alt="숲 테마"
          />
        }></FormControlLabel>

      <FormControlLabel
        labelPlacement="top"
        value="오피스"
        control={<Radio id="SERVER_THEME_002" onChange={handleThemeChange} />}
        label={
          <img
            src="https://images.unsplash.com/photo-1718889339117-d90a40b1250b?q=80&w=1287&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
            width={100}
            alt="숲 테마"
          />
        }></FormControlLabel>
    </RadioGroup>
  );
};

export default ServerThemeSelector;
