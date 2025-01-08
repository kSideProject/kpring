import { create } from "zustand";

interface ChatInputState {
  inputValue: string;
  chatHistory: string[];
  setInputValue: (value: string) => void;
  addChatMessage: (message: string) => void;
}

const useChatInputStore = create<ChatInputState>((set) => ({
  inputValue: "",
  chatHistory: [],
  setInputValue: (value) => set({ inputValue: value }),
  addChatMessage: (message) =>
    set((state) => ({
      chatHistory: [...state.chatHistory, message],
    })),
}));

export default useChatInputStore;
