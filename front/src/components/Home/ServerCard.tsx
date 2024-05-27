import { Card, CardContent, CardMedia, Typography } from "@mui/material";
import React from "react";
import { serverData } from "../../utils/fakeData";

const ServerCard = () => {
  return (
    <div className="flex flex-wrap gap-3 ">
      {serverData.map((server) => {
        return (
          <Card sx={{ width: 345, cursor: "pointer" }}>
            <CardMedia sx={{ height: 140 }} image={server.image}></CardMedia>
            <CardContent>
              <Typography>{server.serverName}</Typography>
              <Typography>{server.members.length}명</Typography>
            </CardContent>
          </Card>
        );
      })}
    </div>
  );
};

export default ServerCard;
