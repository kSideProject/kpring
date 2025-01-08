import React from "react";
import Text from "../atoms/Text";
import Button from "../atoms/Button";
import { CategoriesType } from "@/types/server";
import FavoriteStar from "../atoms/BookmarkStar";
import { useLoginStore } from "@/store/useLoginStore";
import { leaveServer } from "@/api/server";

type ServerInfoProps = {
  hostName: string;
  categories: CategoriesType[] | null;
  serverId: string;
  onDelete: () => void;
  onEnter: () => void;
};

const ServerInfo: React.FC<ServerInfoProps> = ({
  hostName,
  categories,
  serverId,
  onDelete,
  onEnter,
}) => {
  const { accessToken } = useLoginStore();

  const handleOnLeaveServer = async () => {
    try {
      if (serverId && accessToken) {
        await leaveServer(serverId, accessToken);

        console.log("서버탈퇴 성공");
      }
    } catch (err) {
      console.error(err);
    }
  };
  return (
    <div>
      <Text styles="text-p font-bold text-black">
        카테고리: {categories?.map((category) => category.name).join(", ")}
      </Text>
      <Text styles="text-p font-bold text-black">서버장: {hostName}</Text>
      <FavoriteStar id={serverId} />

      <Button onClick={onDelete} style={`bg-primary text-white`}>
        서버삭제
      </Button>
      <Button onClick={onEnter} style={`bg-primary text-white`}>
        서버입장
      </Button>
      <Button onClick={handleOnLeaveServer} style={`bg-primary text-white`}>
        서버탈퇴
      </Button>
    </div>
  );
};

export default ServerInfo;
