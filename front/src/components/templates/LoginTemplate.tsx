import { useNavigate } from "react-router";
import LoginForm from "../organisms/LoginForm";
import Divider from "../atoms/Divider";

const LoginTemplate = () => {
  const navigate = useNavigate();
  return (
    <div className="w-96 flex flex-col justify-center items-center gap-5">
      <LoginForm />
      <Divider style={`bg-white`} />
      <span className="text-white ">
        계정이 없으신가요?{" "}
        <span
          className="text-secondary underline font-bold cursor-pointer"
          onClick={() => navigate("/join")}>
          회원가입
        </span>
        을 클릭해 주세요.
      </span>
    </div>
  );
};

export default LoginTemplate;
