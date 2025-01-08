import React, { ReactNode } from "react";
import { IoCloseCircle } from "react-icons/io5";
import Text from "../atoms/Text";
import useModalStore from "@/store/useModalStore";

interface ModalProps {
  children: ReactNode;
  title: string;
}

const Modal: React.FC<ModalProps> = ({ children, title }) => {
  const { isOpen, closeModal } = useModalStore();
  if (!isOpen) return null;

  return (
    <div className="fixed inset-0 flex flex-col items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
        <div className="flex justify-between">
          <Text styles="text-h5 font-bold text-black">{title}</Text>
          <IoCloseCircle
            onClick={closeModal}
            className="text-h5 mb-3 transition duration-300 hover:text-error cursor-pointer"
          />
        </div>
        <div>{children}</div>
      </div>
    </div>
  );
};

export default Modal;
