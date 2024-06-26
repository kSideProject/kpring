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
import { useNavigate } from "react-router-dom";
import FavoriteStar from "../Home/FavoriteStar";
import ModalComponent from "../Modal/ModalComponent";
import useModal from "../../hooks/Modal";
import Profile from "../Profile/Profile";
import useFetchServers from "../../hooks/FetchServer";
import { ServerResponseType, ServerType } from "../../types/server";

const ServerInfoSidebar: React.FC<ServerInforProps> = ({ close, serverID }) => {
  const token = localStorage.getItem("dicoTown_AccessToken");
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
  const { data } = useFetchServers(token);

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

      <ModalComponent isOpen={isOpen}>
        <Profile closeModal={closeModal} />
      </ModalComponent>
    </>
  );
};

export default ServerInfoSidebar;
