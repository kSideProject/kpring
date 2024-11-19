import AddIcon from "@mui/icons-material/Add";
import {
  Avatar,
  Box,
  Button,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemAvatar,
  Tooltip,
} from "@mui/material";
import { useState } from "react";
import { useNavigate } from "react-router";
// import useFetchServers from "../../hooks/FetchServer";
import useModal from "../common/modal/hooks/useModal";
import { useLoginStore } from "../../store/useLoginStore";
import CreateServerForm from "../Server/CreateServerForm";
import ServerInfoSidebar from "./ServerInfoSidebar";
import { useServerId } from "../../store/useServerId";
import Modal from "../common/modal/Modal";
const LeftSideBar = () => {
  const DRAWER_WIDTH = 88; // 왼쪽 서버 사이드바 넓이
  const [openServerInfo, setOpenServerInfo] = useState(false); // 서버 인포 사이드바 열기
  // const [serverId, setServerId] = useState("");
  const token = localStorage.getItem("dicoTown_AccessToken");
  // const { data } = useFetchServers(token);
  const { clearTokens } = useLoginStore();
  const navigate = useNavigate();
  const { selectedServerId, setServerId } = useServerId();
  const { isOpen, openModal, closeModal } = useModal();

  // console.log(data);
  // 왼쪽 멤버 사이드바 오픈 핸들러
  const handleDrawerOpen = (id: string) => {
    setOpenServerInfo((openServerInfo) => !openServerInfo);
    setServerId(id);
  };

  // 왼쪽 멤버 사이드바 닫기 핸들러
  const handleDrawerClose = () => {
    setOpenServerInfo((openServerInfo) => !openServerInfo);
  };

  // 로그아웃 핸들러
  const handleLogout = () => {
    clearTokens();
    localStorage.removeItem("dicoTown_AccessToken");
    navigate("/login");
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

          {/* {data?.data.map((server) => {
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
          })} */}
        </List>
        <Button
          type="submit"
          variant="contained"
          onClick={handleLogout}
          sx={{
            position: "absolute",
            bottom: "80px",
            display: "flex",
            justifyContent: "center",
            padding: "5px 10px",
            marginLeft: "6px",
          }}>
          로그아웃
        </Button>
      </Drawer>
      <Drawer open={openServerInfo} variant="persistent">
        <ServerInfoSidebar
          open={openServerInfo}
          close={handleDrawerClose}
          serverId={selectedServerId}
        />
      </Drawer>
      <Modal isOpen={isOpen} closeModal={closeModal}>
        <CreateServerForm />
      </Modal>
    </Box>
  );
};

export default LeftSideBar;
