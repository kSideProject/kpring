/*
Axios Interceptor의 역할 : 
자동으로 토큰을 검증하고, 유효하지 않은 경우 자동으로 갱신하는 시스템을 자동화 할 수 있음

1. 자동 토큰 검증(모든 요청 전) 
- 요청을 보내기 전에 액세스 토큰이 있는지 확인
- 액세스 토큰이 있다면 validateAccessToken 함수를 통해 토큰의 유효성 확인
- 토큰이 유효하지 않다면, 자동으로 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급

2. 자동 토큰 갱신(401 응답 처리)
- 서버 응답이 401 Unauthorized인 경우 즉 액세스 토큰이 만료된 경우에 리프레시 토큰을 사용하여 새로운 액세스 토큰 발급
- 새로운 토큰이 발급되면 저장하고 원래 요청 재시도(토큰이 만료되어 처음 실패했던 API 요청을 새로운 토큰으로 요청 시도) 
- 만약 새로운 토큰 발급에 실패하면 사용자를 로그인 페이지로 이동

3. 상태 관리와 로컬 스토리지 동기화
1) 토큰 저장: 
- 새로운 액세스 토큰과 리프레시 토큰을 상태와 로컬 스토리지에 저장

2) 토큰 초기화:
- 토큰 갱신이 실패하거나, 유효하지 않은 토큰이 감지되면 상태와 로컬 스토리지를 초기화

<전체 동작흐름>
요청 인터셉터 (Request Interceptor)
1. 모든 요청 전에 액세스 토큰을 확인
2. 액세스 토큰이 존재하면 validateAccessToken 함수를 호출하여 토큰의 유효성 확인
3. 토큰이 유효하지 않다면, refreshAccessToken 함수를 호출하여 새로운 토큰을 발급받음
4. 토큰이 유효하면 요청 헤더에 액세스 토큰 추가

응답 인터셉터 (Response Interceptor)
1. 서버 응답이 200번대 상태 코드인 경우, 응답을 그대로 반환
2. 서버 응답이 401 Unauthorized인 경우, refreshAccessToken 함수를 호출하여 새로운 토큰을 발급받고 원래 요청 재시도
3. 만약 새로운 토큰 발급에 실패하면, 상태와 로컬 스토리지를 초기화하고 로그인 페이지로 이동
*/

import axios, {
  AxiosError,
  AxiosResponse,
  InternalAxiosRequestConfig,
} from "axios";
import { NavigateFunction } from "react-router-dom";
import { refreshAccessToken, validateAccessToken } from "../api/token";
import { LoginState } from "../store/useLoginStore";

interface RetryConfig extends InternalAxiosRequestConfig {
  _retry?: boolean;
}

// Axios 인터셉터 설정 함수
const interceptorSetup = (store: LoginState, navigate: NavigateFunction) => {
  // 요청 인터셉터 설정
  axios.interceptors.request.use(
    async (config: RetryConfig) => {
      const token = store.accessToken;
      console.log("요청 인터셉터의 Token :", token);

      if (token) {
        // 액세스 토큰 유효성 검사
        const isTokenValid = await validateAccessToken(token);
        console.log("유효한 토큰 : ", isTokenValid);

        if (!isTokenValid) {
          console.log("토큰이 유효하지 않습니다. 새로운 토큰 발급 시도");
          // 액세스 토큰이 유효하지 않으면 리프레시 토큰을 사용해 새로운 액세스 토큰 발급
          const newTokens = await refreshAccessToken(store.refreshToken);
          if (newTokens) {
            // 새로운 토큰을 상태와 로컬 스토리지에 저장
            store.setTokens(newTokens.accessToken, newTokens.refreshToken);
            // 요청 헤더에 새로운 액세스 토큰 추가
            config.headers.Authorization = `Bearer ${newTokens.accessToken}`;
            console.log("새로운 토큰:", newTokens);
          } else {
            // 새로운 토큰 발급 실패 시 로그인 페이지로 이동
            console.error(
              "리프레시 토큰을 이용한 발급이 실패, 로그인 페이지로 이동"
            );
            store.clearTokens();
            navigate("/login");
            return Promise.reject(new Error("토큰이 만료되었습니다."));
          }
        } else {
          // 액세스 토큰이 유효하면 요청 헤더에 추가
          config.headers.Authorization = `Bearer ${token}`;
        }
      }
      return config;
    },
    (error: AxiosError) => {
      // 요청 에러 처리
      console.error("Request Error:", error);
      return Promise.reject(error);
    }
  );
  // 응답 인터셉터 설정
  axios.interceptors.response.use(
    (response: AxiosResponse) => {
      console.log("응답 인터셉터의 response:", response);
      return response;
    },
    async (error: AxiosError) => {
      const originalRequest = error.config as RetryConfig;
      if (
        error.response &&
        // 인증 오류 (401) 처리
        error.response.status === 401 &&
        originalRequest &&
        // 요청이 재시도되지 않았는지 확인
        !originalRequest._retry
      ) {
        originalRequest._retry = true;
        console.log(
          "응답 인터셉터 401 오류, 리프레시 토큰을 토큰을 사용해 새로운 액세스 토큰 발급 "
        );
        // 리프레시 토큰을 사용해 새로운 액세스 토큰 발급
        const newTokens = await refreshAccessToken(store.refreshToken);
        if (newTokens) {
          // 새로운 토큰을 상태와 로컬 스토리지에 저장
          store.setTokens(newTokens.accessToken, newTokens.refreshToken);
          // 원래 요청 헤더에 새로운 액세스 토큰 추가
          originalRequest.headers.Authorization = `Bearer ${newTokens.accessToken}`;
          console.log("401 오류 이후의 새로운 토큰 :", newTokens);
          // 원래 요청을 재시도
          return axios(originalRequest);
        } else {
          // 새로운 토큰 발급 실패 시 로그인 페이지로 이동
          console.error(
            "401 오류 이후 토큰을 새로운 토큰 발급 실패, 로그인페이지로 이동"
          );
          store.clearTokens();
          navigate("/login");
          return Promise.reject(new Error("토큰이 만료되었습니다."));
        }
      }
      // 그 외의 응답 에러 처리
      console.error("응답 오류:", error);
      return Promise.reject(error);
    }
  );
};

export default interceptorSetup;
