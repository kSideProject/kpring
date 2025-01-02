import Avatar from "boring-avatars";
import React from "react";
import Text from "../atoms/Text";

type ServerCardItemProps = {
  serverName: string;
  categories: { id: string; name: string }[] | undefined;
};

const ServerCardItem: React.FC<ServerCardItemProps> = ({
  serverName,
  categories,
}) => {
  return (
    <div className="flex-1 basis-1/4 border">
      <Avatar name={serverName} variant="beam" square height={100}>
        <Avatar />
      </Avatar>
      <Text color="black" size="23">
        {serverName}
        {categories
          ? ` - ${categories.map((category) => category.name).join(", ")}`
          : ""}
      </Text>
    </div>
  );
};

export default ServerCardItem;
