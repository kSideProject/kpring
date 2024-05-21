import {
  Avatar,
  Badge,
  Box,
  Divider,
  List,
  ListItem,
  ListItemAvatar,
  ListItemText,
  styled,
  TextField,
} from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { SideBarProps } from "../../types/layout";
import React, { useState } from "react";
import MemberProfile from "../Profile/MemberProfile";
import DropDown from "../Layout/DropDown";

const FriendsRightSideBar: React.FC<SideBarProps> = ({ close }) => {
  const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-start",
  }));

  const [openProfile, setOpenProfile] = React.useState(false);
  const [openDropDown, setOpenDropDown] = useState(false);
  const toggleDropDown = () => setOpenDropDown(!openDropDown);
  const handleProfileOpen = () => setOpenProfile(true);
  const handleProfileClose = () => setOpenProfile(false);
  const dropDownItems = ['디엠', '그룹', '전체 메세지']
  return (
    <>
      <DrawerHeader className="flex flex-col">
        <Box className="w-full flex py-3 px-1">
          <ArrowForwardIosIcon onClick={close} className="cursor-pointer"/>
          <Box className="w-full flex justify-between">
            <div className="pl-1">친구 목록</div>
            <Box className="relative">
              { openDropDown?
                <KeyboardArrowUpIcon 
                  sx={{color: "purple"}}
                  className="cursor-pointer" 
                  onClick={toggleDropDown}
                />:
                <KeyboardArrowDownIcon className="cursor-pointer" onClick={toggleDropDown}/>
              }
              { openDropDown && 
                <DropDown dropDownItems={dropDownItems}/>
              }
            </Box>
          </Box>
        </Box>
        <TextField 
          placeholder="친구 검색" 
          variant="filled"
          InputProps={{
            sx: {
              '& .MuiFilledInput-input': {
                padding: '15px 12px 8px 12px', // 내부 input 요소의 패딩
              },
            }
          }} 
          sx={{paddingBottom: "1rem"}}
          />
      </DrawerHeader>
      <Divider />
      <List>
        <ListItem>
          <ListItemAvatar onClick={handleProfileOpen}>
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
        closeModal={handleProfileClose}
      ></MemberProfile>
    </>
  );
};

export default FriendsRightSideBar;
