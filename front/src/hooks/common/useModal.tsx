import { useState } from "react";

type ModalType = "profile" | "addServer" | "server" | "addFriend" | null;

const useModal = () => {
  const [isOpen, setIsOpen] = useState(false);
  const [modalType, setModalType] = useState<ModalType>(null);

  const openModal = (type: ModalType) => {
    setModalType(type);
    setIsOpen(true);
  };

  const closeModal = (type: ModalType) => {
    setModalType(null);
    setIsOpen(false);
  };

  return { isOpen, openModal, closeModal, modalType };
};

export default useModal;
