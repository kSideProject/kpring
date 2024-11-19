import { ReactNode } from "react";

type ModalPropsType = {
  children: ReactNode;
  isOpen: boolean;
  closeModal: () => void;
};

const Modal = ({ children, isOpen, closeModal }: ModalPropsType) => {
  if (!isOpen) return null;
  return (
    <div className="fixed inset-0 flex flex-col items-center justify-center bg-black bg-opacity-50 z-50">
      <div className="bg-white p-6 rounded-lg shadow-lg w-full max-w-md">
        {children}
      </div>
      <button onClick={closeModal} className="text-lg">
        X
      </button>
    </div>
  );
};

export default Modal;
