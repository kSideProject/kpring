import {
  Avatar,
  Badge,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  styled,
} from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import { RightSideBarProps } from "../../types/layout";
import React from "react";
import MemberProfile from "../Profile/MemberProfile";

const RightSideBar: React.FC<RightSideBarProps> = ({ close }) => {
  const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-start",
  }));

  const [openProfile, setOpenProfile] = React.useState(false);
  const handleOpen = () => setOpenProfile(true);
  const handleClose = () => setOpenProfile(false);
  return (
    <>
      <DrawerHeader>
        <ArrowForwardIosIcon onClick={close} />
        <div>친구목록</div>
        {/* <div>메세지</div> */}
      </DrawerHeader>
      <Divider />
      <List>
        <ListItem>
          <ListItemAvatar onClick={handleOpen}>
            <Badge
              color="success"
              variant="dot"
              overlap="circular"
              anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
            >
              <Avatar
                alt="user nickname"
                src="https://images.unsplash.com/photo-1517849845537-4d257902454a?q=80&w=2670&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
              />
            </Badge>
          </ListItemAvatar>
          <ListItemText primary="민선님" />
        </ListItem>
      </List>
      <MemberProfile
        openModal={openProfile}
        closeModal={handleClose}
      ></MemberProfile>
    </>
  );
};

export default RightSideBar;
