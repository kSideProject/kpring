import { useNavigate } from "react-router";
import LoginForm from "../organisms/LoginForm";
import Button from "../atoms/Button";
import Divider from "../atoms/Divider";

const LoginTemplate = () => {
  const navigate = useNavigate();
  return (
    <div className="flex flex-col justify-center items-center">
      <LoginForm />
      <Divider />
      <span>계정이 없으신가요?</span>
      <Button onClick={() => navigate("/join")} color="bg-dark">
        회원가입
      </Button>
    </div>
  );
};

export default LoginTemplate;
