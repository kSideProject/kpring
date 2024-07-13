import { Scene } from "phaser";
import { skins } from "../Avatar/avatarAssets";
import { getRandomCostume } from "../../../utils/randomCostume";
import { CostumesData } from "../../../types/avatar";

// ** 서버 접속 시 랜덤으로 캐릭터 생성
const getRandomAssets = (assets: string[]): string => {
  return assets[Math.floor(Math.random() * assets.length)];
};

export const randomSkin = getRandomAssets(skins);

export const createRandomAvatar = (
  scene: Scene,
  x: number,
  y: number
): Phaser.GameObjects.Container => {
  if (scene.textures.exists(randomSkin)) {
    const avatarContainer = scene.add.container(x, y);

    // 아바타 피부색
    const skinSprite = scene.add.sprite(0, 0, randomSkin);
    avatarContainer.add(skinSprite);

    // 아바타 코스튬

    const randomCostumeFrame = getRandomCostume(scene, "top-costume-texture");
    if (randomCostumeFrame) {
      const costumeSprite = scene.add.sprite(
        0,
        0,
        "top-costume-texture",
        randomCostumeFrame
      );
      avatarContainer.add(costumeSprite);
    }

    scene.physics.world.enable(avatarContainer);
    const body = avatarContainer.body as Phaser.Physics.Arcade.Body;
    body.setSize(20, 20);

    return avatarContainer;
  } else {
    throw Error;
  }
};

// ** 아바타 애니메이션
export const avatarAnimation = (scene: Scene) => {
  // scene.anims.create({
  //   key: `${randomSkin}-walk-down`,
  //   frames: scene.anims.generateFrameNames(randomSkin, {
  //     start: 1,
  //     end: 8,
  //     prefix: `${randomSkin}-walk-down-`,
  //     suffix: ".png",
  //   }),
  //   frameRate: 20,
  //   repeat: -1,
  // });
};
