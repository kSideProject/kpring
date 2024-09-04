import React, { useEffect } from "react";
import { ServerInforProps } from "../../types/layout";
import { Divider, Button, styled, Box, Typography } from "@mui/material";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import { useNavigate } from "react-router-dom";
import FavoriteStar from "../Home/FavoriteStar";
import ModalComponent from "../Modal/ModalComponent";
import useModal from "../../hooks/Modal";
import Profile from "../Profile/Profile";
import useFetchServers from "../../hooks/FetchServer";
import axios from "axios";
import { useThemeStore } from "../../store/useThemeStore";

const ServerInfoSidebar: React.FC<ServerInforProps> = ({ close, serverId }) => {
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
  const setTheme = useThemeStore((state) => state.setTheme);
  const { selectedTheme } = useThemeStore();

  const getSelectedServer = async () => {
    const url = `${process.env.REACT_APP_BASE_URL}/server/api/v1/server/${serverId}`;

    try {
      const res = await axios.get(url);
      const results = res.data.data.theme;

      if (results) {
        setTheme(results);
        navigate(`server/${serverId}`);
      }
    } catch (error) {
      console.log(error);
    }
  };

  useEffect(() => {
    getSelectedServer();
  }, [serverId]);

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
          <FavoriteStar id={serverId} />
        </Box>

        <Button
          onClick={() => getSelectedServer()}
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
