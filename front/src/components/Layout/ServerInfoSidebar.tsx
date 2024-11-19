import React, { useEffect, useState } from "react";
// import { ServerInforProps } from "../../../types/layout";
import { Divider, Button, styled, Box, Typography } from "@mui/material";
import ArrowBackIosNewIcon from "@mui/icons-material/ArrowBackIosNew";
import { useNavigate } from "react-router-dom";
// import FavoriteStar from "../../Home/FavoriteStar";
// import useModal from "../../../hooks/Modal";
// import Profile from "../../Profile/Profile";
// import useFetchServers from "../../hooks/FetchServer";
import axios from "axios";
import useModal from "../common/modal/hooks/useModal";
import { SelectedType } from "../../types/server";
import { useThemeStore } from "../../store/useThemeStore";
import { ServerInforProps } from "../../types/layout";
import FavoriteStar from "../Home/FavoriteStar";
import Modal from "../common/modal/Modal";
import Profile from "../Profile/Profile";
// import { useThemeStore } from "../../../store/useThemeStore";
// import { SelectedType, ServerType } from "../../../types/server";
// import { fetchServers } from "../../../hooks/FetchServer";
// import Modal from "../modal/Modal";

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
  // const { data } = useFetchServers(token);
  const setTheme = useThemeStore((state) => state.setTheme);
  const [selectedServer, setSelectedServer] = useState<SelectedType>();

  // useEffect(() => {
  //   fetchServers(token);
  // }, []);

  // console.log(selectedServer);
  const getSelectedServer = async () => {
    const url = `${process.env.REACT_APP_BASE_URL}/server/api/v1/server/${serverId}`;

    try {
      const res = await axios.get(url);
      const results = res.data.data;

      if (results) {
        // console.log(serverId);
        setTheme(results.theme);
        setSelectedServer(results);
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
          <Typography>{selectedServer?.name}</Typography>
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
      <div>
        {selectedServer?.users.map((user) => (
          <span>userID: {user.id}</span>
        ))}
      </div>

      {/* <Modal isOpen={isOpen}>
        <Profile closeModal={closeModal} />
      </Modal> */}
    </>
  );
};

export default ServerInfoSidebar;
