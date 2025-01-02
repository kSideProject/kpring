import Avatar from "boring-avatars";
import { IoAddCircle } from "react-icons/io5";

type AvatarWithAddServerProps = {
  nickname: string | undefined;
  onAvatarClick: () => void;
  onAddServerClick: () => void;
};

const AvatarWithAddServer: React.FC<AvatarWithAddServerProps> = ({
  nickname,
  onAvatarClick,
  onAddServerClick,
}) => {
  return (
    <div className="flex flex-col gap-3">
      <Avatar name={nickname} onClick={onAvatarClick} variant="beam" />
      <IoAddCircle fontSize="48" onClick={onAddServerClick} />
    </div>
  );
};

export default AvatarWithAddServer;
