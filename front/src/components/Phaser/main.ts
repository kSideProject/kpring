import { Boot } from "./scenes/Boot";
// import { GameOver } from "./scenes/GameOver";
import { Game as MainGame } from "./scenes/Game";
// import { MainMenu } from "./scenes/MainMenu";
import { AUTO, Game } from "phaser";
import { Preloader } from "./scenes/Preloader";

const config: Phaser.Types.Core.GameConfig = {
  type: AUTO,
  width: window.innerWidth, // 캔바스 넓이
  height: window.innerHeight, // 캔바스 높이
  parent: "game-container",
  backgroundColor: "A0DEFF",
  scene: [Boot, Preloader, MainGame], // 로딩되는 배경들
};

const StartGame = (parent: string) => {
  return new Game({ ...config, parent });
};

export default StartGame;
