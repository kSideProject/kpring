import React from "react";

type MessageProps = {
  value: string;
};

// TODO: 스타일 변경
const Message: React.FC<MessageProps> = ({ value }) => {
  return <p className="text-red-500">{value}</p>;
};

export default Message;
