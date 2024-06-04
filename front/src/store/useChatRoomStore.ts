import { create } from "zustand";

interface ChatRoomState{
    isChatRoomShow: boolean;
    setIsChatRoomShow: (payload: boolean) => void;
}

// TODO : 채팅방 전역 상태 정의
const useChatRoomStore = create<ChatRoomState>(set =>({
    isChatRoomShow : false,
    setIsChatRoomShow: (payload: boolean)=>set(()=>({isChatRoomShow: payload}))
}))



export default useChatRoomStore;