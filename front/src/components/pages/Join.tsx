import Text from "../atoms/Text";
import JoinTemplate from "../templates/JoinTemplate";

const Join = () => {
  return (
    <div className="bg-black h-svh flex flex-col items-center justify-center">
      <Text styles="text-h4 text-white font-bold mb-5">회원가입</Text>
      <JoinTemplate />
    </div>
  );
};

export default Join;
