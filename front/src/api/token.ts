import axios from "axios";

// 토큰 검증 API
async function validateAccessToken(token: string) {
  try {
    const response = await axios.post(
      "http://kpring.duckdns.org/auth/api/v2/validation",
      {},
      {
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${token}`,
        },
      }
    );

    if (response.status === 200) {
      console.log("토큰 검증 성공:", response.data);
      return true;
    } else {
      console.error("토큰 검증 실패:", response.data);
      return false;
    }
  } catch (error) {
    console.error("토큰 검증 중 오류 발생:", error);
    return false;
  }
}

// 새로운 accessToken 요청
async function refreshAccessToken(refreshToken: string) {
  try {
    const response = await axios.post(
      "http://kpring.duckdns.org/auth/api/v1/acess_token",
      {
        refreshToken,
      },
      {
        headers: {
          "Content-Type": "application/json",
        },
      }
    );

    if (response.status === 200) {
      console.log("accessToken 갱신 성공:", response.data);
      return response.data.data;
    } else {
      console.error("accessToken 갱신 실패:", response.data);
      return null;
    }
  } catch (error) {
    console.error("API 호출 중 오류 발생:", error);
    return null;
  }
}

export { refreshAccessToken, validateAccessToken };
