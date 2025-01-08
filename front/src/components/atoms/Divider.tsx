import React from "react";

interface DividerProps {
  style: string;
}

const Divider: React.FC<DividerProps> = ({ style }) => {
  return <div className={`w-full h-[.5px] ${style}`}></div>;
};

export default Divider;
