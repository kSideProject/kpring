import { Scene } from "phaser";
import { skins } from "../Avatar/avatarAssets";
import { getRandomCostume } from "../Avatar/randomCostume";
import { createAnimations } from "../Avatar/createAnimations";

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
    const skinSprite = scene.add.sprite(7, 5, randomSkin);
    avatarContainer.add(skinSprite);

    // 아바타 코스튬(상의)
    const randomTopCostumeFrame = getRandomCostume(
      scene,
      "top-costume-texture"
    );
    if (randomTopCostumeFrame) {
      const costumeSprite = scene.add.sprite(
        7,
        5,
        "top-costume-texture",
        randomTopCostumeFrame.frame
      );
      avatarContainer.add(costumeSprite);
    }

    // 아바타 코스튬(하의)
    const randomBottomCostumeFrame = getRandomCostume(
      scene,
      "bottom-costume-texture"
    );
    if (randomBottomCostumeFrame) {
      const costumeSprite = scene.add.sprite(
        7,
        5,
        "bottom-costume-texture",
        randomBottomCostumeFrame.frame
      );
      avatarContainer.add(costumeSprite);
    }

    // 아바타 헤어
    const randomHairFrame = getRandomCostume(scene, "hair-texture");
    if (randomHairFrame) {
      const costumeSprite = scene.add.sprite(
        7,
        5,
        "hair-texture",
        randomHairFrame.frame
      );
      avatarContainer.add(costumeSprite);
    }

    if (randomBottomCostumeFrame && randomTopCostumeFrame && randomHairFrame) {
      createAnimations(
        scene,
        randomSkin,
        randomTopCostumeFrame?.key,
        randomTopCostumeFrame?.color,
        randomBottomCostumeFrame?.key,
        randomBottomCostumeFrame?.color,
        randomHairFrame?.key,
        randomHairFrame?.color
      );
    }

    scene.physics.world.enable(avatarContainer);
    const body = avatarContainer.body as Phaser.Physics.Arcade.Body;
    body.setSize(14, 20);

    return avatarContainer;
  } else {
    throw Error;
  }
};
