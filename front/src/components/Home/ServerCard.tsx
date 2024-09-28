import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import FavoriteStar from "./FavoriteStar";
import { ServerCardProps } from "../../types/server";

const ServerCard: React.FC<ServerCardProps> = ({ server }) => {
  return (
    <div className="flex flex-wrap gap-3 ">
      <Card sx={{ width: 345, cursor: "pointer" }}>
        {/* <CardMedia sx={{ height: 140 }} image={server.image}></CardMedia>
        <CardContent>
          <Typography>{server.serverName}</Typography>
          <Typography>{server.members.length}명</Typography>
          <FavoriteStar id={server.serverId} />
        </CardContent> */}
      </Card>
    </div>
  );
};

export default ServerCard;
