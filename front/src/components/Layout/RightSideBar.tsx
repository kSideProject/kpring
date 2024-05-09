import {
  Avatar,
  Badge,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  Typography,
  styled,
} from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import React from "react";

const RightSideBar = () => {
  const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-start",
  }));
  return (
    <>
      <DrawerHeader>
        <ArrowForwardIosIcon />
        <div>친구목록</div>
        {/* <div>메세지</div> */}
      </DrawerHeader>
      <Divider />
      <List>
        <ListItem>
          <ListItemAvatar>
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

        <ListItem>
          <ListItemAvatar>
            <Badge
              color="success"
              variant="dot"
              overlap="circular"
              anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
            >
              <Avatar
                alt="user nickname"
                src="https://images.unsplash.com/photo-1542272201-b1ca555f8505?q=80&w=2574&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
              />
            </Badge>
          </ListItemAvatar>
          <ListItemText primary="동근님" />
        </ListItem>

        <ListItem>
          <ListItemAvatar>
            <Badge
              color="warning"
              variant="dot"
              overlap="circular"
              anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
            >
              <Avatar alt="user nickname" />
            </Badge>
          </ListItemAvatar>
          <ListItemText primary="다원님" />
        </ListItem>

        <ListItem>
          <ListItemAvatar>
            <Badge
              color="error"
              variant="dot"
              overlap="circular"
              anchorOrigin={{ vertical: "bottom", horizontal: "right" }}
            >
              <Avatar
                alt="user nickname"
                src="https://images.unsplash.com/photo-1560790671-b76ca4de55ef?q=80&w=2092&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D"
              />
            </Badge>
          </ListItemAvatar>
          <ListItemText primary="민아님" />
        </ListItem>
      </List>
    </>
  );
};

export default RightSideBar;
