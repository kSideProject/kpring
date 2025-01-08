import Text from "../atoms/Text";
import LoginTemplate from "../templates/LoginTemplate";

const Login = () => {
  return (
    <div className="bg-black h-svh flex flex-col items-center justify-center ">
      <div className="bg-darkBlack p-10 rounded-lg drop-shadow-xl">
        <Text styles="text-h4 text-white text-center font-bold mb-5">
          로그인
        </Text>
        <LoginTemplate />
      </div>
    </div>
  );
};

export default Login;
