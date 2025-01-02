import { useCallback } from "react";
import useChatRoomStore from "@/store/useChatRoomStore";

// * 대화하고 싶은 친구를 클릭했을 때 대화를 입력하고, 말풍선이 보여는지 컴포넌트

const ChatRoomSideBar = () => {
  const setIsChatRoomShow = useChatRoomStore(
    (state) => state.setIsChatRoomShow
  );
  const handleChatRoomClose = useCallback(() => {
    setIsChatRoomShow(false);
  }, [setIsChatRoomShow]);

  return (
    <></>
    // <DrawerHeader>
    //   <ArrowForwardIosIcon
    //     onClick={handleChatRoomClose}
    //     sx={{ color: "white", cursor: "pointer" }}
    //   />
    //   <Box>
    //     <div>다이렉트 메세지</div>
    //     <ChatInputField />
    //   </Box>
    // </DrawerHeader>
  );
};

export default ChatRoomSideBar;
