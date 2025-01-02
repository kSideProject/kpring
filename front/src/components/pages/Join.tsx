import Text from "../atoms/Text";
import JoinTemplate from "../templates/JoinTemplate";

const Join = () => {
  return (
    <div className="h-screen flex flex-col items-center justify-center">
      <Text color="text-dark" size="32">
        회원가입
      </Text>
      <JoinTemplate />
    </div>
  );
};

export default Join;
