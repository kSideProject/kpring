import { useEffect, useState } from "react";
import { serverData } from "../utils/fakeData";
import ServerCardList from "../components/Home/ServerCardList";

const Home = () => {
  const [servers, setServers] = useState<
    { serverId: string; serverName: string; image: string; members: string[] }[]
  >([]);

  useEffect(() => {
    setServers(serverData);
  }, []);

  return (
    <div className="my-24 mx-28">
      <div className="">인기있는 서버들</div>

      <h2 className="text-2xl">즐겨찾기한 서버들</h2>
      <ServerCardList servers={servers} />
    </div>
  );
};

export default Home;
