import { create, StateCreator } from "zustand";
import { ThemeType } from "../types/server";
import { persist, devtools } from "zustand/middleware";

interface ThemeState {
  selectedTheme: ThemeType | null;
  setTheme: (theme: ThemeType | null) => void;
}

const themeStore: StateCreator<ThemeState> = (set) => ({
  selectedTheme: null,
  setTheme: (theme: ThemeType | null) => set({ selectedTheme: theme }),
});

export const useThemeStore = create<ThemeState>()(
  devtools(persist(themeStore, { name: "theme" }))
);
