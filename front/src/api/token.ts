import axiosInstance from "./axiosInstance";
interface TokenPayload {
  exp: number; // 만료 시간 (유닉스 타임스탬프)
}

// Base64로 인코딩된 JWT 토큰을 디코딩하는 함수
function parseJwt(token: string): TokenPayload | null {
  try {
    const base64Url = token.split(".")[1];
    if (!base64Url) {
      console.error("Invalid token format");
      return null;
    }

    const base64 = base64Url.replace(/-/g, "+").replace(/_/g, "/");
    const jsonPayload = decodeURIComponent(
      atob(base64)
        .split("")
        .map((c) => "%" + ("00" + c.charCodeAt(0).toString(16)).slice(-2))
        .join("")
    );

    return JSON.parse(jsonPayload);
  } catch (error) {
    console.error("Error decoding token:", error);
    return null;
  }
}

// JWT 토큰의 만료 시간을 추출하는 함수
export function getTokenExpiration(token: string): number {
  const decoded = parseJwt(token);
  if (!decoded || !decoded.exp) {
    console.error("Invalid token or missing expiration time");
    return Date.now(); // 만료 시간을 찾을 수 없으면 현재 시간 반환
  }
  return decoded.exp * 1000; // 초 단위에서 밀리초로 변환
}

// 토큰 검증 API
export async function validateAccessToken(token: string): Promise<boolean> {
  console.log("액세스토큰:", token);
  try {
    const response = await axiosInstance.post(
      "/auth/api/v2/validation",
      {},
      {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      }
    );
    // console.log("액세스 토큰 응답 상태(status):", response.status);
    // console.log("액세스토큰 응답 데이터:", response.data);

    if (response.status === 200) {
      // console.log("토큰 검증 성공:", response.data);
      return true;
    } else {
      // console.error("토큰 검증 실패:", response.data);
      return false;
    }
  } catch (error) {
    // console.error("토큰 검증 중 오류 발생:", error);
    return false;
  }
}

// 새로운 accessToken 요청
export async function refreshAccessToken(
  refreshToken: string
): Promise<{ accessToken: string; refreshToken: string } | null> {
  // console.log("리프레시 토큰으로 새로운 액세스 토큰 요청:", refreshToken);
  try {
    const response = await axiosInstance.post(
      "/auth/api/v1/access_token",
      { refreshToken },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );
    // console.log("새로운 액세스 토큰 응답 상태(status):", response.status);
    // console.log("새로운 액세스 토큰 응답 데이터 :", response.data);

    if (response.status === 200) {
      // console.log("accessToken 갱신 성공:", response.data);
      return response.data.data;
    } else {
      // console.error("accessToken 갱신 실패:", response.data);
      return null;
    }
  } catch (error) {
    // console.error("API 호출 중 오류 발생:", error);
    return null;
  }
}
