// ** 이 파일은 Phaser 게임에 대한 화면 크기, 물리 엔진 등 환경 설정을 하는 파일 입니다. ** //
// ** https://newdocs.phaser.io/docs/3.70.0/Phaser.Types.Core.GameConfig ** //

import { AUTO, Game } from "phaser";
import { Preloader } from "./Scenes/Preloader";
import { Map } from "./Scenes/Map";

const config: Phaser.Types.Core.GameConfig = {
  type: AUTO,
  parent: "map-container",
  physics: {
    default: "arcade",
    arcade: { debug: false },
  },
  scene: [
    //     Boot,
    Preloader,
    Map,
    //     MainMenu,
    //     MainGame,
    //     GameOver
  ],
};

const EnterServer = (parent: string) => {
  return new Game({ ...config, parent });
};

export default EnterServer;
