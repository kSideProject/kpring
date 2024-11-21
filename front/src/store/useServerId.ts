import { create } from "zustand";

interface ServerId {
  selectedServerId: string;
  setServerId: (id: string) => void;
}

export const useServerId = create<ServerId>((set) => ({
  selectedServerId: "",
  setServerId: (id) => set({ selectedServerId: id }),
}));
