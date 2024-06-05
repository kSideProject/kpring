import { Box, Button, Modal, Typography } from "@mui/material";
import React from "react";
import useModal from "../../hooks/Modal";

const CreateServerForm = () => {
  const { isOpen, closeModal, openModal } = useModal();

  return (
    <div>
      <form>
        <label>서버이름</label>
        <input />
      </form>
    </div>
  );
};

export default CreateServerForm;
