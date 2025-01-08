import Hero from "../organisms/Hero";
import ServerCards from "../templates/ServerCards";

const Home = () => {
  return (
    <div className="bg-black w-full min-h-screen">
      <div className="w-[80%] mx-auto">
        <Hero />
        <ServerCards />
      </div>
    </div>
  );
};

export default Home;
