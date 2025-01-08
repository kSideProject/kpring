import { create } from "zustand";

type ModalType =
  | "addServer"
  | "showProfile"
  | "addFriend"
  | "showServer"
  | null;

interface ModalState {
  isOpen: boolean;
  openModal: (type: ModalType) => void;
  closeModal: () => void;
  modalType: ModalType;
}

const useModalStore = create<ModalState>((set) => ({
  isOpen: false,
  modalType: null,
  openModal: (type) => set({ isOpen: true, modalType: type }),
  closeModal: () => set({ isOpen: false, modalType: null }),
}));

export default useModalStore;
