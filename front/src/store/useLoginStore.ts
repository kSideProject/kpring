import { validateAccessToken } from "@/api/auth";
import Cookies from "js-cookie";
import { create } from "zustand";

export interface LoginState {
  accessToken: string;
  userId: string | null;
  setTokens: (accessToken: string, refreshToken: string) => Promise<boolean>;
  clearTokens: () => void;
  isLoggedIn: () => boolean;
}

export const useLoginStore = create<LoginState>((set, get) => ({
  // 초기 상태 설정
  accessToken: localStorage.getItem("dicoTown_AccessToken") || "",
  userId: null,

  // 토큰 및 상태 설정
  setTokens: async (accessToken, refreshToken) => {
    const { isValid, userId } = await validateAccessToken(accessToken);

    if (isValid && userId) {
      // Zustand 상태 업데이트
      set({ accessToken, userId });

      // 로컬 스토리지 및 쿠키 업데이트
      localStorage.setItem("dicoTown_AccessToken", accessToken);
      Cookies.set("dicoTown_RefreshToken", refreshToken, {
        expires: 1,
        secure: true,
        sameSite: "Strict",
      });
      return true;
    }

    console.error("토큰 검증 실패");
    return false;
  },

  // 상태 초기화
  clearTokens: () => {
    set({ accessToken: "", userId: null });
    localStorage.removeItem("dicoTown_AccessToken");
    Cookies.remove("dicoTown_RefreshToken");
  },

  // 로그인 여부 확인
  isLoggedIn: () => !!get().accessToken, // get 파라미터로 상태 참조
}));
