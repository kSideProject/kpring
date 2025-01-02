import React from "react";
import PreviewServerMap from "../molecules/PreviewServerMap";
import Text from "../atoms/Text";

const Hero = () => {
  return (
    <div>
      <Text color="black" size="text-18">
        새로운 테마
      </Text>
      <PreviewServerMap />
    </div>
  );
};

export default Hero;
