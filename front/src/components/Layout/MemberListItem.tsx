import { Member } from "../../types/layout";
import {
  Avatar,
  Badge,
  ListItem,
  ListItemAvatar,
  ListItemText,
} from "@mui/material";
import useChatRoomStore from "../../store/useChatRoomStore";
import { useCallback } from "react";
interface MemberListItemProps {
  member: Member;
  handleProfileOpen: () => void;
}

const MemberListItem: React.FC<MemberListItemProps> = ({
  member,
  handleProfileOpen,
}) => {
  const setIsChatRoomShow = useChatRoomStore(
    (state) => state.setIsChatRoomShow
  );
  const handleChatRoomOpen = useCallback(() => {
    setIsChatRoomShow(true);
  }, [setIsChatRoomShow]);

  return (
    <div>
      <ListItem
        sx={{
          "&:hover": {
            backgroundColor: "#FDE2F3",
          },
        }}>
        <ListItemAvatar onClick={handleProfileOpen}>
          <Badge
            color="success"
            variant="dot"
            overlap="circular"
            sx={{ cursor: "pointer" }}
            anchorOrigin={{ vertical: "bottom", horizontal: "right" }}>
            <Avatar alt="user nickname" src={member.profilePath} />
          </Badge>
        </ListItemAvatar>
        <ListItemText
          primary={member.userName}
          sx={{ cursor: "pointer" }}
          onClick={handleChatRoomOpen}
        />
      </ListItem>
    </div>
  );
};

export default MemberListItem;
