import { Box, Modal } from "@mui/material";
import React, { ReactNode } from "react";
import { modalStyle } from "../../types/modal";

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
