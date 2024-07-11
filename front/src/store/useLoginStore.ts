import create from "zustand";

export interface LoginState {
  accessToken: string;
  refreshToken: string;
  setTokens: (accessToken: string, refreshToken: string) => void;
  clearTokens: () => void;
}

export const useLoginStore = create<LoginState>((set) => ({
  accessToken: localStorage.getItem("dicoTown_AccessToken") || "",
  refreshToken: localStorage.getItem("dicoTown_RefreshToken") || "",
  setTokens: (accessToken, refreshToken) => {
    set({ accessToken, refreshToken });
    localStorage.setItem("dicoTown_AccessToken", accessToken);
    localStorage.setItem("dicoTown_RefreshToken", refreshToken);
  },
  clearTokens: () => {
    set({ accessToken: "", refreshToken: "" });
    localStorage.removeItem("dicoTown_AccessToken");
    localStorage.removeItem("dicoTown_RefreshToken");
  },
}));
