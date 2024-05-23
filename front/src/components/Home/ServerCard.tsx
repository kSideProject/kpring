import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import StarBorderIcon from "@mui/icons-material/StarBorder";
import StarIcon from "@mui/icons-material/Star";
import { useEffect, useState } from "react";
import { serverData } from "../../utils/fakeData";
import { FavoritesState } from "../../types/server";

const ServerCard = () => {
  const [favorites, setFavorites] = useState<FavoritesState>({}); // 각각의 카드를 관리할 수 있는 객체
  const [filteredServers, setFilteredServers] = useState(serverData); // api fetch data를 기본으로 설정하고 여기서 필터

  // 즐겨찾기 아이콘을 클릭했을 때 일어나는 이벤트
  const onClickFavorite = (id: string) => {
    // 즐겨찾기 아이콘 토글
    setFavorites((prevFavorites) => {
      const newFavorites = {
        ...prevFavorites, // 원래 즐겨찾기가 되어있는 카드(서버)들을 그대로 유지 (불변성 유지)
        [id]: !prevFavorites[id], // 매개변수로 받은 서버의 id 값의 상태를 반전 업데이트(토글 형식)
      };

      // 전체 서버에서 즐겨찾기 된 서버만 필터링
      const newFilteredServers = serverData.filter(
        (server) => newFavorites[server.serverId]
      );

      // 필터된 서버로 업데이트/저장
      setFilteredServers(newFilteredServers);
      return newFavorites;
    });
  };

  useEffect(() => {
    const initialFilteredServers = serverData.filter(
      (server) => favorites[server.serverId]
    );
    setFilteredServers(initialFilteredServers);
  }, [favorites]);

  return (
    <div className="flex flex-wrap gap-3 ">
      {filteredServers.map((server) => {
        const isFavorite = favorites[server.serverId];

        return (
          <Card sx={{ width: 345, cursor: "pointer" }} key={server.serverId}>
            <CardMedia sx={{ height: 140 }} image={server.image}></CardMedia>
            <CardContent>
              <Typography>{server.serverName}</Typography>
              <Typography>{server.members.length}명</Typography>

              {isFavorite ? (
                <StarBorderIcon
                  onClick={() => onClickFavorite(server.serverId)}
                />
              ) : (
                <StarIcon onClick={() => onClickFavorite(server.serverId)} />
              )}
            </CardContent>
          </Card>
        );
      })}
    </div>
  );
};

export default ServerCard;
