import {
  Avatar,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemAvatar,
  ListItemButton,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import React from "react";

const LeftSideBar = () => {
  return (
    <Drawer variant="permanent" sx={{ width: 40, top: 64 }}>
      <List sx={{ display: "flex", flexDirection: "column" }}>
        <ListItem alignItems="center">
          <ListItemButton>
            <AddIcon></AddIcon>
          </ListItemButton>
        </ListItem>
        <Divider />

        <ListItem alignItems="center">
          <ListItemAvatar
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Avatar />
          </ListItemAvatar>
        </ListItem>

        <ListItem alignItems="center">
          <ListItemAvatar
            sx={{
              display: "flex",
              justifyContent: "center",
              alignItems: "center",
            }}
          >
            <Avatar />
          </ListItemAvatar>
        </ListItem>
      </List>
    </Drawer>
  );
};

export default LeftSideBar;
