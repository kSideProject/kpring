import axiosInstance from "./axiosInstance";

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
