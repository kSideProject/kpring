import Text from "../atoms/Text";

const Hero = () => {
  return (
    <div className="py-10">
      <Text styles="text-h4 font-bold text-white">새로운 테마</Text>
      <div className="flex justify-between h-96">
        <img
          src="/assets/map/camping/camping.png"
          alt="캠핑테마 이미지"
          className="rounded-md object-cover"
          width="100%"
        />
      </div>
    </div>
  );
};

export default Hero;
