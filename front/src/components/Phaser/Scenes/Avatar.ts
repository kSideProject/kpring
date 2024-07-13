import { Scene } from "phaser";
import { skins } from "../Avatar/avatarAssets";
import { getRandomCostume } from "../../../utils/randomCostume";

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

    // 아바타 코스튬(상의)
    const randomTopCostumeFrame = getRandomCostume(
      scene,
      "top-costume-texture"
    );
    if (randomTopCostumeFrame) {
      const costumeSprite = scene.add.sprite(
        0,
        0,
        "top-costume-texture",
        randomTopCostumeFrame
      );
      avatarContainer.add(costumeSprite);
    }

    // 아바타 코스튬(하의)
    const randomCostumeFrame = getRandomCostume(
      scene,
      "bottom-costume-texture"
    );
    if (randomCostumeFrame) {
      const costumeSprite = scene.add.sprite(
        0,
        0,
        "bottom-costume-texture",
        randomCostumeFrame
      );
      avatarContainer.add(costumeSprite);
    }

    // 아바타 헤어
    const randomHairFrame = getRandomCostume(scene, "hair-texture");
    if (randomHairFrame) {
      const costumeSprite = scene.add.sprite(
        0,
        0,
        "hair-texture",
        randomHairFrame
      );
      avatarContainer.add(costumeSprite);
    }

    scene.physics.world.enable(avatarContainer);
    const body = avatarContainer.body as Phaser.Physics.Arcade.Body;
    body.setSize(50, 50);

    return avatarContainer;
  } else {
    throw Error;
  }
};
