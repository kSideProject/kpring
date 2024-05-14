import { Avatar, Box, Button, Modal, Typography } from "@mui/material";
import React from "react";
import {
  MemberProfileModalProps,
  memberProfileModalStyle,
} from "../../types/layout";

const MemberProfile: React.FC<MemberProfileModalProps> = ({
  openModal,
  closeModal,
}) => {
  return (
    <div>
      <Modal
        keepMounted
        open={openModal}
        aria-labelledby="keep-mounted-madal-title"
        aria-describedby="keep-mounted-madal-description"
      >
        <Box sx={memberProfileModalStyle}>
          <Avatar></Avatar>
          <Typography id="keep-mounted-madal-title">User Name</Typography>
          <Button onClick={closeModal}>닫기</Button>
        </Box>
      </Modal>
    </div>
  );
};

export default MemberProfile;
