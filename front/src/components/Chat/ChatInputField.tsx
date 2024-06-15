import { TextField } from "@mui/material";
import useChatInputStore from "../../store/useChatInputStore";
import { Send } from "@mui/icons-material";

const ChatInputField = () => {
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

  return (
    <form onSubmit={handleSendMessage}>
      <TextField
        type="text"
        value={chatInputValue}
        onChange={(e) => setChatInputValue(e.target.value)}
        sx={{ wordBreak: "break" }}
      />

      <Send></Send>
    </form>
  );
};

export default ChatInputField;
