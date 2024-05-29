import {
  Box,
  Divider,
  styled,
  TextField,
} from "@mui/material";
import ArrowForwardIosIcon from "@mui/icons-material/ArrowForwardIos";
import KeyboardArrowDownIcon from '@mui/icons-material/KeyboardArrowDown';
import KeyboardArrowUpIcon from '@mui/icons-material/KeyboardArrowUp';
import { SideBarProps } from "../../types/layout";
import React, { useState } from "react";
import DropDown from "../Layout/DropDown";
import MemberList from "./MemberList";
import { messageMemberList } from "../../utils/fakeData";

const FriendsRightSideBar: React.FC<SideBarProps> = ({ close }) => {
  const DrawerHeader = styled("div")(({ theme }) => ({
    display: "flex",
    height: "8rem",
    alignItems: "center",
    padding: theme.spacing(0, 1),
    ...theme.mixins.toolbar,
    justifyContent: "flex-start",
  }));

  const [openDropDown, setOpenDropDown] = useState(false);
  const toggleDropDown = () => setOpenDropDown(!openDropDown);
  const dropDownItems = ['디엠', '그룹', '전체 메세지']

  return (
    <>
      <DrawerHeader className="flex-col">
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
      <MemberList memberList={messageMemberList}/>
    </>
  );
};

export default FriendsRightSideBar;
