import useChatInputStore from "@/store/useChatInputStore";
import TextInput from "../atoms/TextInput";
import { BsSend } from "react-icons/bs";

// TODO:
const ChatInput = () => {
  const chatInputValue = useChatInputStore((state) => state.inputValue);
  const setChatInputValue = useChatInputStore((state) => state.setInputValue);

  const handleSendMessage = (e: React.FormEvent<HTMLFormElement>) => {
    e.preventDefault();
    if (chatInputValue.trim() !== "") {
      setChatInputValue(chatInputValue);

      const event = new CustomEvent("updateBalloonText", {
        detail: chatInputValue,
      });
      window.dispatchEvent(event);

      setChatInputValue("");
    }
  };

  const onChangeChat = () => {
    // TODO: 채팅 내용
  };

  return (
    <form onSubmit={handleSendMessage}>
      <TextInput value="" onChange={onChangeChat} name="chat" />
      <BsSend />
    </form>
  );
};

export default ChatInput;
