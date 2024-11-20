import React from "react";

const MessageTab = ({
  onClick,
}: {
  onClick: (tab: "messages" | "new friends") => void;
}) => {
  return (
    <div className="flex justify-around items-center">
      <button
        className="bg-slate-500 px-2 py-1 rounded-sm"
        onClick={() => onClick("messages")}>
        메세지 목록
      </button>
      <button
        className="bg-slate-500 px-2 py-1 rounded-sm"
        onClick={() => onClick("new friends")}>
        새로운 친구 요청
      </button>
    </div>
  );
};

export default MessageTab;
