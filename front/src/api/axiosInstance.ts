/*
이 인스턴스가 필요한 이유 : 
--------------------------------------------------------
<로직>
토큰이 유효한가?
토큰이 유효하지 않다면 리프레시 토큰으로 발급
발급실패 시 로그인페이지로 이동  
--------------------------------------------------------
axios 인터셉터로 위의 로직대로 코드 작성 하다가 요청 인터셉터가 계속해서 같은 요청을 반복하는 상황이 발생, 
token.ts의 두 함수(validateAccessToken, refreshAccessToken)에서 요청을 보내는 부분이 인터셉터에 의해 다시 가로채지고 있는 것으로 추정
이를 해결하기 위한 방법으로 아래의 인스턴스를 별도 만들어서 해결함
*/
import axios from "axios";

const axiosInstance = axios.create({
  baseURL: process.env.REACT_APP_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export default axiosInstance;
