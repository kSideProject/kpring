import Text from "../atoms/Text";
import LoginTemplate from "../templates/LoginTemplate";

const Login = () => {
  return (
    <div className="h-svh flex flex-col items-center justify-center">
      <Text color="text-dark" size="">
        로그인
      </Text>
      <LoginTemplate />
    </div>
  );
};

export default Login;
