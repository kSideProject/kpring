import { useEffect, useState } from "react";
import ServerCardList from "../components/Home/ServerCardList";
import { Button } from "@mui/material";
import ServerThemeSelector from "../components/Server/ServerThemeSelector";
import PreviewServerMap from "../components/Server/PreviewServerMap";

const Home = () => {
  // const [servers, setServers] = useState<
  //   { serverId: string; serverName: string; image: string; members: string[] }[]
  // >([]);

  // useEffect(() => {
  //   setServers(serverData);
  // }, []);

  return (
    <div className="my-24 mx-28">
      <div className="flex gap-5">새로운 테마</div>

      <PreviewServerMap />

      <h2 className="text-2xl">즐겨찾기한 서버들</h2>
      {/* <ServerCardList servers={servers} /> */}
    </div>
  );
};

export default Home;
