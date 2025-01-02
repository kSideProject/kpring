import Text from "../atoms/Text";
import JoinTemplate from "../templates/JoinTemplate";

const Join = () => {
  return (
    <div className="bg-black h-svh flex flex-col items-center justify-center">
      <div className="bg-darkBlack p-10 rounded-lg drop-shadow-xl">
        <Text styles="text-h4 text-white text-center font-bold mb-5">
          회원가입
        </Text>
        <JoinTemplate />
      </div>
    </div>
  );
};

export default Join;
