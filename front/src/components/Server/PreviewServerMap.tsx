import React from "react";
import { useThemeStore } from "../../store/useThemeStore";
import { useNavigate } from "react-router";
import { ThemeType } from "../../types/server";

const PreviewServerMap = () => {
  const setTheme = useThemeStore((state) => state.setTheme);
  const navigate = useNavigate();

  const onClickTheme = (selectedtheme: ThemeType, themeTitle: string) => {
    setTheme(selectedtheme);
    navigate(`/${themeTitle}`);
  };

  return (
    <div>
      <div
        onClick={() =>
          onClickTheme({ id: "SERVER_THEME_001", name: "숲" }, "camping")
        }>
        Camping
      </div>
      <div
        onClick={() =>
          onClickTheme({ id: "SERVER_THEME_002", name: "오피스" }, "beach")
        }>
        Beach
      </div>
    </div>
  );
};

export default PreviewServerMap;
