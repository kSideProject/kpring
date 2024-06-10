import { Box, Modal } from "@mui/material";
import { ReactNode } from "react";
import { modalStyle } from "../../style/modal";

interface ModalComponentProps {
  children: ReactNode;
  isOpen: boolean;
}

const ModalComponent = ({ children, isOpen }: ModalComponentProps) => {
  return (
    <Modal
      open={isOpen}
      aria-labelledby="parent-modal-title"
      aria-describedby="parent-modal-description">
      <Box sx={modalStyle}>{children}</Box>
    </Modal>
  );
};

export default ModalComponent;
