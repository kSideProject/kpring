import { Box } from "@mui/material";
import React from "react";
import ServerCard from "../components/Home/ServerCard";

const Home = () => {
  return (
    <div className="my-24 mx-28">
      <div className="">인기있는 서버들</div>

      <h2 className="text-2xl">최근에 방문한 서버</h2>
      <ServerCard />
    </div>
  );
};

export default Home;
