import { ReactNode } from "react";
import { IoCloseCircle } from "react-icons/io5";

type ModalPropsType = {
  children: ReactNode;
  isOpen: boolean;
  closeModal: () => void;
};

const Modal = ({ children, isOpen, closeModal }: ModalPropsType) => {
  if (!isOpen) return null;
  return (
    <div className="fixed inset-0 flex flex-col items-center justify-center bg-black bg-opacity-50 z-50">
      <IoCloseCircle
        onClick={closeModal}
        className="text-3xl text-white mb-3 hover:text-red-500"
      />

      <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-md">
        {children}
      </div>
    </div>
  );
};

export default Modal;
