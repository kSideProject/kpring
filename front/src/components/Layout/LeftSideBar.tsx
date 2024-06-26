import {
  Avatar,
  Box,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemAvatar,
  ListItemButton,
  Tooltip,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import { useState } from "react";
import ServerInfoSidebar from "./ServerInfoSidebar";
import CreateServerForm from "../Server/CreateServerForm";
import useModal from "../../hooks/Modal";
import ModalComponent from "../Modal/ModalComponent";
import useFetchServers from "../../hooks/FetchServer";

const LeftSideBar = () => {
  const DRAWER_WIDTH = 88; // 왼쪽 서버 사이드바 넓이
  const [openServerInfo, setOpenServerInfo] = useState(false); // 서버 인포 사이드바 열기
  const { isOpen, openModal } = useModal();
  const [serverId, setServerId] = useState("");
  const token = localStorage.getItem("dicoTown_AccessToken");
  const { data } = useFetchServers(token);

  // 왼쪽 멤버 사이드바 오픈 핸들러
  const handleDrawerOpen = (id: string) => {
    setOpenServerInfo((openServerInfo) => !openServerInfo);
    setServerId(id);
  };

  // 왼쪽 멤버 사이드바 닫기 핸들러
  const handleDrawerClose = () => {
    setOpenServerInfo((openServerInfo) => !openServerInfo);
  };

  return (
    <Box sx={{ position: "absolute" }}>
      <Drawer
        variant="permanent"
        sx={{
          width: DRAWER_WIDTH,
          marginTop: "64px",
          "& .MuiDrawer-paper": {
            width: DRAWER_WIDTH,
            marginTop: "64px",
          },
        }}>
        <List sx={{ display: "flex", flexDirection: "column" }}>
          <ListItem
            sx={{
              display: "flex",
              flexDirection: "column",
              gap: "20px",
              alignItems: "center",
            }}>
            <Avatar></Avatar>
            <AddIcon onClick={openModal}></AddIcon>
          </ListItem>
          <Divider />

          {data?.data.map((server) => {
            return (
              <ListItem alignItems="center" key={server.id}>
                <Tooltip title={server.name}>
                  <ListItemAvatar
                    sx={{
                      display: "flex",
                      justifyContent: "center",
                      alignItems: "center",
                    }}>
                    <Avatar onClick={() => handleDrawerOpen(server.id)}>
                      {server.name}
                    </Avatar>
                  </ListItemAvatar>
                </Tooltip>
              </ListItem>
            );
          })}
        </List>
      </Drawer>
      <Drawer open={openServerInfo} variant="persistent">
        <ServerInfoSidebar
          open={openServerInfo}
          close={handleDrawerClose}
          serverID={serverId}
        />
      </Drawer>
      <ModalComponent isOpen={isOpen}>
        <CreateServerForm></CreateServerForm>
      </ModalComponent>
    </Box>
  );
};

export default LeftSideBar;
