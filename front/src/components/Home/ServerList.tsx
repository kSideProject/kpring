import React from "react";
import ServerCard from "./ServerCard";
import { useFilterFavorites } from "../../hooks/Favorites";

const ServerList = ({
  servers,
}: {
  servers: {
    serverId: string;
    serverName: string;
    image: string;
    members: string[];
  }[];
}) => {
  const filteredFavorite = useFilterFavorites();

  return (
    <>
      {servers
        .filter((server) => filteredFavorite.includes(server.serverId))
        .map((server) => (
          <ServerCard key={server.serverId} servers={server} />
        ))}
    </>
  );
};

export default ServerList;
