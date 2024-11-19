import PreviewServerMap from "../components/Home/PreviewServerMap";

const Home = () => {
  return (
    <div className="my-24 mx-72">
      <div>
        <h2 className="text-2xl font-bold">새로운 테마</h2>
        <PreviewServerMap />
      </div>

      <div>
        <h2 className="text-2xl font-bold">즐겨찾기한 서버들</h2>
      </div>
    </div>
  );
};

export default Home;
