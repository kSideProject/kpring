import React from "react";
import { useThemeStore } from "../../store/useThemeStore";
import { ServerMap } from "../phaser/ServerMap";

const ServerMapWithTheme = () => {
  const selectedTheme = useThemeStore((state) => state.selectedTheme);
  return <ServerMap selectedTheme={selectedTheme} />;
};

export default ServerMapWithTheme;
