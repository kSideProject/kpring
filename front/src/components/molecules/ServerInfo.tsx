import React from "react";
import Text from "../atoms/Text";
import Button from "../atoms/Button";
import { CategoriesType } from "@/types/server";
import FavoriteStar from "../atoms/BookmarkStar";

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
  return (
    <div>
      <Text styles="text-h2 font-bold text-black">
        {categories?.map((category) => category.name).join(", ")}
      </Text>
      <Text styles="text-h2 font-bold text-black">{hostName}</Text>
      <FavoriteStar id={serverId} />

      <Button onClick={onDelete} style={`bg-primary text-white`}>
        서버삭제
      </Button>
      <Button onClick={onEnter} style={`bg-primary text-white`}>
        서버입장
      </Button>
    </div>
  );
};

export default ServerInfo;
