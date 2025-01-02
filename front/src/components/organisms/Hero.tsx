import React from "react";
import PreviewServerMap from "../molecules/PreviewServerMap";
import Text from "../atoms/Text";

const Hero = () => {
  return (
    <div>
      <Text styles="text-h1 font-bold text-black">새로운 테마</Text>
      <PreviewServerMap />
    </div>
  );
};

export default Hero;
