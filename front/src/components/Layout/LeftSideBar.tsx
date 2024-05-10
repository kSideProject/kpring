import {
  Avatar,
  Divider,
  Drawer,
  List,
  ListItem,
  ListItemAvatar,
  ListItemButton,
  Tooltip,
} from "@mui/material";
import AddIcon from "@mui/icons-material/Add";
import React from "react";

const serverData = [
  {
    serverName: "server1",
    member: 112,
    category: "game",
    image:
      "https://images.unsplash.com/photo-1715276611440-6c0e90aa132b?q=80&w=2369&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
  },
  {
    serverName: "server2",
    member: 133,
    category: "education",
    image:
      "https://images.unsplash.com/photo-1715157163446-91abdd457a97?q=80&w=2487&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
  },
  {
    serverName: "server3",
    member: 13223,
    category: "coding",
    image:
      "https://images.unsplash.com/photo-1461749280684-dccba630e2f6?q=80&w=2369&auto=format&fit=crop&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D",
  },
];

const LeftSideBar = () => {
  return (
    <Drawer variant="permanent" sx={{ width: 40 }}>
      <List sx={{ display: "flex", flexDirection: "column" }}>
        <ListItem alignItems="center">
          <ListItemButton>
            <AddIcon></AddIcon>
          </ListItemButton>
        </ListItem>
        <Divider />

        {serverData.map((server) => {
          return (
            <ListItem alignItems="center">
              <Tooltip title={server.serverName}>
                <ListItemAvatar
                  sx={{
                    display: "flex",
                    justifyContent: "center",
                    alignItems: "center",
                  }}
                >
                  <Avatar src={server.image} />
                </ListItemAvatar>
              </Tooltip>
            </ListItem>
          );
        })}
      </List>
    </Drawer>
  );
};

export default LeftSideBar;
