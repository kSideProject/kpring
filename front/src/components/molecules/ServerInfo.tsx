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
      <Text color="red" size="23">
        {categories?.map((category) => category.name).join(", ")}
      </Text>
      <Text color="red" size="23">
        {hostName}
      </Text>
      <FavoriteStar id={serverId} />

      <Button onClick={onDelete} color="red">
        서버삭제
      </Button>
      <Button onClick={onEnter} color="red">
        서버입장
      </Button>
    </div>
  );
};

export default ServerInfo;
