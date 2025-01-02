import Text from "../atoms/Text";
import LoginTemplate from "../templates/LoginTemplate";

const Login = () => {
  return (
    <div className="bg-black h-svh flex flex-col items-center justify-center">
      <Text styles="text-h4 text-white font-bold mb-5">로그인</Text>
      <LoginTemplate />
    </div>
  );
};

export default Login;
