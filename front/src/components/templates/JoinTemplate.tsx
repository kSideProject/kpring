import { useNavigate } from "react-router";
import JoinForm from "../organisms/JoinForm";
import Button from "../atoms/Button";
import Divider from "../atoms/Divider";

const JoinTemplate = () => {
  const navigate = useNavigate();
  return (
    <div className="flex flex-col justify-center items-center">
      <JoinForm />
      <Divider />
      <span>이미 계정이 있으시면 로그인을 클릭해주세요.</span>
      <Button onClick={() => navigate("login")} color="bg-dark">
        로그인
      </Button>
    </div>
  );
};

export default JoinTemplate;
