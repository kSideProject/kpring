import ServerCard from "../components/Home/ServerCard";
import Carousel from "react-material-ui-carousel";
import { serverData } from "../utils/fakeData";

const Home = () => {
  return (
    <div className="my-24 mx-36 text-cente">
      <div className="flex flex-col gap-3 mb-12">
        <div className="text-2xl">인기있는 서버들</div>
        <Carousel autoPlay={true} height={400}>
          {serverData.map((server) => {
            return (
              <div className="h-[400px]">
                <img src={server.image} alt={server.serverName} />
                <p className="absolute bottom-10 left-2 text-3xl">
                  {server.serverName}
                </p>
              </div>
            );
          })}
        </Carousel>
      </div>

      <div className="flex flex-col gap-3">
        <h2 className="text-2xl">즐겨찾기한 서버들</h2>
        <ServerCard />
      </div>
    </div>
  );
};

export default Home;
