import { useCallback } from "react";
import useChatRoomStore from "../../store/useChatRoomStore";
import { Box, styled } from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";

import ChatInputField from "./ChatInputField";

const ChatRoomSideBar = () => {
  const setIsChatRoomShow = useChatRoomStore(
    (state) => state.setIsChatRoomShow
  );
  const handleChatRoomClose = useCallback(() => {
    setIsChatRoomShow(false);

  }, [setIsChatRoomShow]);

  },[setIsChatRoomShow])

  const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    height: "8rem",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-start",
  }));

  return (

    <DrawerHeader>
      <ArrowForwardIosIcon
        onClick={handleChatRoomClose}
        sx={{ color: "white" }}
        className="cursor-pointer"

    <DrawerHeader >
      <ArrowForwardIosIcon 
      onClick={handleChatRoomClose} 
      sx={{color:"white", cursor:'pointer'}}

      />
      <Box>
        <div>다이렉트 메세지</div>
        <ChatInputField />
      </Box>
    </DrawerHeader>
  );
};

export default ChatRoomSideBar;
