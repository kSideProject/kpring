import ServerCard from "./ServerCard";
import { useFilterFavorites } from "../../hooks/FavoriteServer";
import { ServerCardListProps, ServerType } from "../../types/server";

const ServerCardList: React.FC<ServerCardListProps> = ({ servers }) => {
  const favoriteItems = useFilterFavorites();

  return (
    <>
      {/* {servers
        .filter((server: ServerType) => favoriteItems.includes(server.serverId))
        .map((server: ServerType) => (
          <ServerCard key={server.serverId} server={server} />
        ))} */}
    </>
  );
};

export default ServerCardList;
