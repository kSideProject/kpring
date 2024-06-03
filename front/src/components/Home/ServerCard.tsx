import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import FavoriteStar from "./FavoriteStar";

const ServerCard = ({
  servers,
}: {
  servers: {
    serverId: string;
    serverName: string;
    image: string;
    members: string[];
  };
}) => {
  return (
    <div className="flex flex-wrap gap-3 ">
      <Card sx={{ width: 345, cursor: "pointer" }}>
        <CardMedia sx={{ height: 140 }} image={servers.image}></CardMedia>
        <CardContent>
          <Typography>{servers.serverName}</Typography>
          <Typography>{servers.members.length}명</Typography>
          <FavoriteStar id={servers.serverId} />
        </CardContent>
      </Card>
    </div>
  );
};

export default ServerCard;
