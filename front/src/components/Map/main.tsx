import { Game } from "phaser";
import { Preloader } from "./ServerMap/Preloader";
import { MainMap } from "./ServerMap/MainMap";

const config: Phaser.Types.Core.GameConfig = {
  type: Phaser.AUTO, // Phaser가 웹GL 또는 캔버스를 자동으로 선택해서 렌더링
  scene: [Preloader, MainMap], // 사용할 씬들을 배열로 지정
  physics: {
    // 물리 엔진 설정
    default: "arcade", // 충돌 감지와 기본적인 물리 효과 제공
    arcade: {
      debug: true, // 디버그모드 활성화 시 충돌 영역과 물리 효과 확인하고 조정가능
    },
  },
};

const EnterServer = (parent: string) => {
  const map = new Game({ ...config, parent }); // config 객체에 정의된 설정과 parent를 합쳐 새로운 인스턴스 생성
  map.scene.start("MainMap", { serverInstance: map }); // MainMap를 시작하고 serverInstance라는 이름으로 현재 서버 인스턴스 전달

  return map; // 생성된 인스턴스를 반환
};

export default EnterServer;
