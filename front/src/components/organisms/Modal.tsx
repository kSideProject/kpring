import React, { ReactNode } from "react";
import { IoCloseCircle } from "react-icons/io5";
import Text from "../atoms/Text";

type ModalProps = {
  children: ReactNode;
  isOpen: boolean;
  closeModal: () => void;
  title: string;
};

const Modal: React.FC<ModalProps> = ({
  children,
  isOpen,
  closeModal,
  title,
}) => {
  if (!isOpen) return null;
  return (
    <div className="fixed inset-0 flex flex-col items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-white p-8 rounded-lg shadow-lg w-full max-w-md">
        <div className="flex justify-between">
          <Text size="text-xl" color="black">
            {title}
          </Text>
          <IoCloseCircle
            onClick={closeModal}
            className="text-3xl mb-3 hover:text-red-500"
          />
        </div>
        <div>{children}</div>
      </div>
    </div>
  );
};

export default Modal;
