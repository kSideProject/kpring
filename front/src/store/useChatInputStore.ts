import create from "zustand";

interface ChatInputState {
  inputValue: string;
  setInputValue: (value: string) => void;
}

const useChatInputStore = create<ChatInputState>((set) => ({
  inputValue: "",
  setInputValue: (value) => set({ inputValue: value }),
}));

export default useChatInputStore;
