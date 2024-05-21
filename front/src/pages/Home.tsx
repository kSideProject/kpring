import ServerCard from "../components/Home/ServerCard";

const Home = () => {
  return (
    <div className="my-24 mx-28">
      <div className="">인기있는 서버들</div>

      {/* API에 사용자가 서버에 접속한 마지막 시간을 기록할 수 있나요? */}
      <h2 className="text-2xl">최근에 방문한 서버</h2>
      <ServerCard />
    </div>
  );
};

export default Home;
