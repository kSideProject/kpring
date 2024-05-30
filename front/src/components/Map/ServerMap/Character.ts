import { Scene } from "phaser";

// 캐릭터 생성
export const createCharacter = (
  scene: Scene,
  x: number,
  y: number
): Phaser.Physics.Arcade.Sprite => {
  if (scene.textures.exists("basic_character")) {
    // "basic_character" 라는 텍스처 로드가 완료 되면 캐릭터 생성
    return scene.physics.add.sprite(
      x, // 캐릭터가 생성되면 기본 x축의 위치
      y, // 캐릭터가 생성되면 기본 y축의 위치
      "basic_character", // preload파일에서 atlas의 key값과 동일한 keyㄴ값
      "move-down-2.png" // 움직이지 않는 상태의 기본 캐릭터
    );
  } else {
    throw new Error("캐릭터가 생성되지 않았습니다.");
  }
};

// 캐릭터 애니메이션
export const charanterAnimation = (scene: Scene) => {
  // Move-Down
  scene.anims.create({
    key: "basic_character_move_down",
    frames: scene.anims.generateFrameNames("basic_character", {
      start: 1,
      end: 4,
      prefix: "move-down-",
      suffix: ".png",
    }),
    frameRate: 15,
    repeat: -1,
  });
  // Move-Up
  scene.anims.create({
    key: "basic_character_move_up",
    frames: scene.anims.generateFrameNames("basic_character", {
      start: 1,
      end: 4,
      prefix: "move-up-",
      suffix: ".png",
    }),
    frameRate: 15,
    repeat: -1,
  });

  // Move-Left
  scene.anims.create({
    key: "basic_character_move_left",
    frames: scene.anims.generateFrameNames("basic_character", {
      start: 1,
      end: 4,
      prefix: "move-left-",
      suffix: ".png",
    }),
    frameRate: 15,
    repeat: -1,
  });

  // Move-Right
  scene.anims.create({
    key: "basic_character_move_right",
    frames: scene.anims.generateFrameNames("basic_character", {
      start: 1,
      end: 4,
      prefix: "move-right-",
      suffix: ".png",
    }),
    frameRate: 15,
    repeat: -1,
  });
};
