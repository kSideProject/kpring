import { useThemeStore } from "@/store/useThemeStore";
import React from "react";
import { ServerMap } from "../Phaser/ServerMap";

const Server = () => {
  const selectedTheme = useThemeStore((state) => state.selectedTheme);
  return <ServerMap selectedTheme={selectedTheme} />;
};

export default Server;
