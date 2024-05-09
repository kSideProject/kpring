import React, { useState } from "react";
import MuiAppBar, { AppBarProps as MuiAppBarProps } from "@mui/material/AppBar";
import {
  Box,
  Drawer,
  Toolbar,
  Typography,
  styled,
  useTheme,
} from "@mui/material";
import SupervisedUserCircleIcon from "@mui/icons-material/SupervisedUserCircle";
import ChatBubbleIcon from "@mui/icons-material/ChatBubble";
import RightSideBar from "./RightSideBar";

interface AppBarProps extends MuiAppBarProps {
  open?: boolean;
}

const Header = () => {
  //   const theme = useTheme();

  const DRAWER_WIDTH = 240; // 오른쪽 사이드바 넓이
  const [open, setOpen] = useState(false); // 사이드바 열고 닫는 상태
  const [selectedOption, setSelectedOption] = useState("");

  // 오른쪽 사이드바 오픈 핸들러
  const handleDrawerOpen = () => {
    setOpen(true);
  };

  // 오른쪽 사이드바 닫기 핸들러
  const handleDrawerClose = () => {
    setOpen(false);
  };

  // 상단 네브바
  const AppBar = styled(MuiAppBar, {
    shouldForwardProp: (prop) => prop !== "open",
  })<AppBarProps>(({ theme, open }) => ({
    transition: theme.transitions.create(["margin", "width"], {
      easing: theme.transitions.easing.sharp,
      duration: theme.transitions.duration.leavingScreen,
    }),
    ...(open && {
      width: `calc(100% - ${DRAWER_WIDTH}px)`,
      transition: theme.transitions.create(["margin", "width"], {
        easing: theme.transitions.easing.easeOut,
        duration: theme.transitions.duration.enteringScreen,
      }),
      marginRight: DRAWER_WIDTH,
    }),
  }));

  return (
    <header>
      <AppBar position="fixed" open={open}>
        <Toolbar sx={{ display: "flex", justifyContent: "space-between" }}>
          <Typography variant="h6"> Dicotown</Typography>
          <Box sx={{ display: "flex", gap: 2 }}>
            <ChatBubbleIcon onClick={handleDrawerOpen} />
            <SupervisedUserCircleIcon onClick={handleDrawerOpen} />
          </Box>
        </Toolbar>
      </AppBar>
      <Drawer
        sx={{
          width: DRAWER_WIDTH,
          flexShrink: 0,
          "& .MuiDrawer-paper": {
            width: DRAWER_WIDTH,
          },
        }}
        variant="persistent"
        anchor="right"
        open={open}
      >
        <RightSideBar close={handleDrawerClose} />
      </Drawer>
    </header>
  );
};

export default Header;
