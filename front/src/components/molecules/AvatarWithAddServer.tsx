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
    <div className="flex flex-col gap-2 group-hover:items-start w-full p-2">
      <div
        className="flex items-center ml-1 gap-2 cursor-pointer"
        onClick={onAvatarClick}>
        <Avatar
          name={nickname}
          variant="beam"
          size={42}
          className="transition duration-300 hover:opacity-80 shadow-sm"
        />
        <span className="hidden group-hover:inline-block text-small font-semibold">
          {nickname}
        </span>
      </div>
      <div
        className="flex items-center gap-2 cursor-pointer"
        onClick={onAddServerClick}>
        <IoAddCircle fontSize="48" />
        <span className="hidden group-hover:inline-block text-small font-semibold">
          서버 추가
        </span>
      </div>
    </div>
  );
};

export default AvatarWithAddServer;
