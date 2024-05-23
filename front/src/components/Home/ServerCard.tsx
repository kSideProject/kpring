import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import FavoriteIcon from "./FavoriteIcon";

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
    <Card sx={{ width: 345, cursor: "pointer" }} key={servers.serverId}>
      <CardMedia sx={{ height: 140 }} image={servers.image}></CardMedia>
      <CardContent>
        <Typography>{servers.serverName}</Typography>
        <Typography>{servers.members.length} 명</Typography>
        <FavoriteIcon id={servers.serverId} />
      </CardContent>
    </Card>
  );
};

export default ServerCard;
