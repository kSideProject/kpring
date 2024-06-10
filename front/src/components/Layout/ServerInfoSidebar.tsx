import React, { useState } from "react";
import { ServerInforProps } from "../../types/layout";
import {
  Avatar,
  Badge,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Button,
  styled,
  Modal,
  Box,
  Typography,
} from "@mui/material";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import { serverData } from "../../utils/fakeData";
import { useNavigate } from "react-router-dom";
import FavoriteStar from "../Home/FavoriteStar";
import ModalComponent from "../Modal/ModalComponent";
import useModal from "../../hooks/Modal";
import Profile from "../Profile/Profile";

const ServerInfoSidebar: React.FC<ServerInforProps> = ({ close, serverID }) => {
  const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-start",
    flexDirection: "column",
    width: "240px",
  }));
  const navigate = useNavigate();
  const { isOpen, openModal, closeModal } = useModal();

  return (
    <>
      <DrawerHeader>
        <Box
          sx={{
            display: "flex",
            justifyContent: "space-between",
            alignItems: "center",
            gap: "10px",
            m: "10px",
            width: "100%",
          }}>
          <ArrowBackIosNewIcon onClick={close} />
          <Typography>서버이름</Typography>
          <FavoriteStar id={serverID} />
        </Box>

        <Button
          onClick={() => navigate(`server/${serverID}`)}
          sx={{
            backgroundColor: "#2A2F4F",
            width: "100%",
            color: "#fff",
            "&:hover": {
              backgroundColor: "#917FB3",
            },
          }}>
          서버입장
        </Button>
      </DrawerHeader>
      <Divider />
      <List>
        {serverData
          .filter((server) => server.serverId === serverID)
          .map((member) => {
            return (
              <ListItem>
                <ListItemAvatar onClick={openModal}>
                  <Badge
                    color="success"
                    variant="dot"
                    overlap="circular"
                    anchorOrigin={{ vertical: "bottom", horizontal: "right" }}>
                    <Avatar
                      alt="user nickname"
                      src="https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
                    />
                  </Badge>
                </ListItemAvatar>
                <ListItemText primary={member.members} />
              </ListItem>
            );
          })}
      </List>
      <ModalComponent isOpen={isOpen}>
        <Profile closeModal={closeModal} />
      </ModalComponent>
    </>
  );
};

export default ServerInfoSidebar;
