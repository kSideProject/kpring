import { Avatar, Button, Typography } from "@mui/material";

interface ProfileProps {
  closeModal: () => void;
}

const Profile = ({ closeModal }: ProfileProps) => {
  return (
    <>
      <Avatar></Avatar>
      <Typography id="keep-mounted-madal-title">User Name</Typography>
      <Button onClick={closeModal}>닫기</Button>
    </>
  );
};

export default Profile;
