import Hero from "../organisms/Hero";
import ServerCards from "../templates/ServerCards";

const Home = () => {
  return (
    <div className="max-w-7xl p-7">
      <Hero />
      <ServerCards />
    </div>
  );
};

export default Home;
