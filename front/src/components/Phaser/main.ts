// ** 이 파일은 Phaser 게임에 대한 화면 크기, 물리 엔진 등 환경 설정을 하는 파일 입니다. ** //
// ** https://newdocs.phaser.io/docs/3.70.0/Phaser.Types.Core.GameConfig ** //

import { AUTO, Game } from "phaser";
import { CampingPreloader } from "./Scenes/CampingPreloader";
import { CampingMap } from "./MapTheme/CampingMap";
import { BeachPreloader } from "./Scenes/BeachPreloader";
import { BeachMap } from "./MapTheme/BeachMap";
import { ThemeType } from "../../types/server";
import AnimatedTiles from "phaser-animated-tiles-phaser3.5";

const config: Phaser.Types.Core.GameConfig = {
  type: AUTO,
  parent: "map-container",
  width: window.innerWidth,
  height: window.innerHeight,
  physics: {
    default: "arcade",
    arcade: { debug: true },
  },
  scale: {
    mode: Phaser.Scale.RESIZE,
    autoCenter: Phaser.Scale.CENTER_BOTH,
  },
  plugins: {
    scene: [
      {
        key: "AnimatedTiles",
        plugin: AnimatedTiles,
        mapping: "animatedTiles",
        sceneKey: "animatedTiles",
      },
    ],
  },
};

export const EnterServer = (theme: ThemeType | null, parent: string) => {
  let scenes: Phaser.Types.Scenes.SceneType[] = [];

  if (theme?.name === "숲") {
    scenes = [CampingPreloader, CampingMap];
  } else if (theme?.name === "오피스") {
    scenes = [BeachPreloader, BeachMap];
  }
  return new Game({ ...config, parent, scene: scenes });
};

export default EnterServer;
