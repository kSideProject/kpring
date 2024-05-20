import { AUTO, Game } from "phaser";
import { Preloader } from "./serverScene/Preloader";
import { MainServer } from "./serverScene/MainServer";

//  Find out more information about the Game Config at:
//  https://newdocs.phaser.io/docs/3.70.0/Phaser.Types.Core.GameConfig
const config: Phaser.Types.Core.GameConfig = {
  type: Phaser.AUTO,
  width: 1600,
  height: 840,
  scene: [Preloader, MainServer],
  scale: {
    mode: Phaser.Scale.FIT,
    // autoCenter: Phaser.Scale.CENTER_BOTH,
    parent: "game-container",
  },
};

const StartGame = (parent: string) => {
  return new Game({ ...config, parent });
};

export default StartGame;
