import React, { useCallback, useRef } from "react";
import useChatRoomStore from "../../store/useChatRoomStore";
import { Box, TextField, styled } from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import useChatInputStore from "../../store/useChatInputStore";

const ChatRoomSideBar = () => {
  const setIsChatRoomShow = useChatRoomStore(
    (state) => state.setIsChatRoomShow
  );
  const handleChatRoomClose = useCallback(() => {
    setIsChatRoomShow(false);
  }, [setIsChatRoomShow]);

  const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    height: "8rem",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-start",
  }));

  // Chat Input
  const chatInputRef = useRef<HTMLInputElement>(null);
  const setChatInputValue = useChatInputStore((state) => state.setInputValue);

  const handleClearTextField = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (chatInputRef.current && e.key === "Enter") {
      chatInputRef.current.value = "";
    }
  };

  const handleChatInputChange = useCallback(
    (e: React.ChangeEvent<HTMLInputElement>) => {
      setChatInputValue(e.target.value);
    },
    [setChatInputValue]
  );

  return (
    <DrawerHeader>
      <ArrowForwardIosIcon
        onClick={handleChatRoomClose}
        sx={{ color: "white" }}
        className="cursor-pointer"
      />
      <Box>
        <div>다이렉트 메세지</div>
        <TextField
          inputRef={chatInputRef}
          onChange={handleChatInputChange}
          onKeyDown={handleClearTextField}
          sx={{ color: "white" }}
        />
      </Box>
    </DrawerHeader>
  );
};

export default ChatRoomSideBar;
