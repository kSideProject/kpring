import React from "react";
import Text from "../atoms/Text";
import FavoriteStar from "../atoms/BookmarkStar";
import Button from "../atoms/Button";

type ServerCardItemProps = {
  id: string;
  serverName: string;
  categories: { id: string; name: string }[] | undefined;
};

const ServerCardItem: React.FC<ServerCardItemProps> = ({
  serverName,
  categories,
  id,
}) => {
  return (
    <div className="relative group border-[1px] border-gray rounded-md h-36 p-2 overflow-hidden shadow-sm">
      <div
        className="absolute inset-0 bg-gradient-to-r from-primary  to-tertiary 
              opacity-0 group-hover:opacity-30 
              bg-[length:200%_200%] group-hover:bg-[length:100%_100%] 
              transition-all duration-500 "></div>
      <div className="flex flex-col justify-between z-10 p-3">
        <div>
          <div className="flex justify-between items-center">
            <Text styles="text-h6 text-white font-bold text-black">
              {serverName}
            </Text>
            <FavoriteStar id={id} />
          </div>
          <Text styles="text-small text-white">
            {categories &&
              `${categories.map((category) => category.name).join(", ")}`}
          </Text>
        </div>
        <span
          className="absolute bottom-0 right-2 opacity-0 
           group-hover:translate-y-0 group-hover:opacity-100 
           transition-all duration-500 text-white px-4 py-2 rounded-lg shadow-lg">
          입장하기
        </span>
      </div>
    </div>
  );
};

export default ServerCardItem;
