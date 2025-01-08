import { useNavigate } from "react-router";
import JoinForm from "../organisms/JoinForm";
import Divider from "../atoms/Divider";

const JoinTemplate = () => {
  const navigate = useNavigate();
  return (
    <div className="w-96 flex flex-col justify-center items-center gap-5">
      <JoinForm />
      <Divider style={`bg-white`} />
      <span className="text-white">
        이미 계정이 있으시면{" "}
        <span
          className="text-secondary underline font-bold cursor-pointer"
          onClick={() => navigate("login")}>
          로그인
        </span>
        을 클릭해주세요.
      </span>
    </div>
  );
};

export default JoinTemplate;
