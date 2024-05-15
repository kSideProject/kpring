import { AUTO, Game } from "phaser";
import { Preloader } from "./serverScene/Preloader";
import { MainServer } from "./serverScene/MainServer";

//  Find out more information about the Game Config at:
//  https://newdocs.phaser.io/docs/3.70.0/Phaser.Types.Core.GameConfig
const config: Phaser.Types.Core.GameConfig = {
  type: AUTO,
  width: 1200,
  height: 800,
  parent: "game-container",
  scene: [Preloader, MainServer],
  scale: {},
};

const StartGame = (parent: string) => {
  return new Game({ ...config, parent });
};

export default StartGame;
