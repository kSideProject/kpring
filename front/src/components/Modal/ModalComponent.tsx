import { Box, Modal } from "@mui/material";
import { ReactNode } from "react";
import { globalColors } from "../../style/globalStyles";

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
      <Box
        sx={{
          position: "absolute" as "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 400,
          bgcolor: globalColors.primary,
          boxShadow: 24,
          p: 4,
        }}>
        {children}
      </Box>
    </Modal>
  );
};

export default ModalComponent;
