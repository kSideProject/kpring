import { List } from "@mui/material";
import MemberListItem from "./MemberListItem";
import MemberProfile from "../Profile/MemberProfile";
import React from "react";
import { Member } from "../../types/layout";

interface MemberListProps{
    memberList: Member[]
}

// TODO : 오른쪽 사이드바 멤버 리스트
const MemberList : React.FC<MemberListProps> = ({memberList}) => {
    const [openProfile, setOpenProfile] = React.useState(false);
    const handleProfileOpen = () => setOpenProfile(true);
    const handleProfileClose = () => setOpenProfile(false);

    return (
    <>
        <List className="overflow-auto scrollbar-hide">
            {memberList.map(member=>(
                <MemberListItem member={member} handleProfileOpen = {handleProfileOpen}/>
            ))}     
        </List>
        <MemberProfile
            openModal={openProfile}
            closeModal={handleProfileClose}
        ></MemberProfile>
    </>

  )
}

export default MemberList