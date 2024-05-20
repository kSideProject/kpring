import { Game } from "phaser";
import { Preloader } from "./serverScene/Preloader";
import { MainServer } from "./serverScene/MainServer";

const config: Phaser.Types.Core.GameConfig = {
  type: Phaser.AUTO, // Phaserrk 웹GL 또는 캔버스를 자동으로 선택해서 렌더링

  scene: [Preloader, MainServer], // 사용할 씬들을 배열로 지정

  physics: {
    default: "arcade",
    arcade: {
      debug: false,
    },
  },
};

const EnterServer = (parent: string) => {
  const server = new Game({ ...config, parent }); // config 객체에 정의된 설정과 parent를 합쳐 새로운 인스턴스 생성
  server.scene.start("MainSever", { serverInstance: server }); // MainSever를 시작하고 serverIstance라는 이름으로 현재 게임 인스턴스 전달

  return server; // 생성된 인스턴스를 반환
};

export default EnterServer;
