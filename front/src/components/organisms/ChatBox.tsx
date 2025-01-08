import useChatInputStore from "@/store/useChatInputStore";
import TextInput from "../atoms/TextInput";
import { BsSend } from "react-icons/bs";
import { useState } from "react";
import { RiArrowDownWideFill, RiArrowUpWideFill } from "react-icons/ri";

const ChatBox = () => {
  const chatInputValue = useChatInputStore((state) => state.inputValue);
  const setChatInputValue = useChatInputStore((state) => state.setInputValue);
  const addChatMessage = useChatInputStore((state) => state.addChatMessage);
  const [expandChatBox, setExpandChatBox] = useState<boolean>(true);

  // 챗 박스 드래그로 옮기기
  const [position, setPosition] = useState({ x: 100, y: 100 });
  const [dragging, setDragging] = useState(false);
  const [offset, setOffset] = useState({ x: 0, y: 0 });

  const handleMouseDown = (e: React.MouseEvent<HTMLDivElement>) => {
    setDragging(true);
    setOffset({
      x: e.clientX - position.x,
      y: e.clientY - position.y,
    });
  };

  const handleMouseMove = (e: React.MouseEvent<HTMLDivElement>) => {
    if (dragging) {
      const newX = Math.max(
        0,
        Math.min(window.innerWidth - 300, e.clientX - offset.x)
      );
      const newY = Math.max(
        0,
        Math.min(window.innerHeight - 500, e.clientY - offset.y)
      );
      setPosition({
        x: newX,
        y: newY,
      });
    }
  };

  const handleMouseUp = () => {
    setDragging(false);
  };

  const handleSendMessage = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    const message = chatInputValue.trim();
    if (message !== "") {
      addChatMessage(message);
      const event = new CustomEvent("chatMessage", { detail: message });
      window.dispatchEvent(event);
      setChatInputValue("");
    }
  };

  const handleExpandChatBox = () => {
    setExpandChatBox((prev) => !prev);
  };

  return (
    <div>
      <div
        className={`absolute bg-white 
        ${dragging ? "cursor-grabbing" : "cursor-grab"}
      `}
        style={{
          left: `${position.x}px`,
          top: `${position.y}px`,
          width: "300px",
          height: `${expandChatBox ? "500px" : "100px"}`,
        }}
        onMouseDown={handleMouseDown}
        onMouseMove={handleMouseMove}
        onMouseUp={handleMouseUp}>
        {expandChatBox ? (
          <RiArrowDownWideFill
            onClick={handleExpandChatBox}
            className="cursor-pointer"
          />
        ) : (
          <RiArrowUpWideFill
            onClick={handleExpandChatBox}
            className="cursor-pointer"
          />
        )}
        <ul>
          {/* TODO: 채팅내용 */}
          <li>Hello</li>
        </ul>
        <form onSubmit={handleSendMessage} className="flex w-full">
          <TextInput
            value={chatInputValue}
            onChange={(e) => setChatInputValue(e.target.value)}
            name="chat"
          />
          <BsSend />
        </form>
      </div>
    </div>
  );
};

export default ChatBox;
