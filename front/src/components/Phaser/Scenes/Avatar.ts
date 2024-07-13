import { Scene } from "phaser";
import { bottom, skins, top } from "../Avatar/avatarAssets";

// ** 서버 접속 시 랜덤으로 캐릭터 생성
const getRandomAssets = (assets: string[]): string => {
  return assets[Math.floor(Math.random() * assets.length)];
};

export const randomSkin = getRandomAssets(skins);
export const randomTop = getRandomAssets(top);
export const randomBottom = getRandomAssets(bottom);

export const createRandomAvatar = (
  scene: Scene,
  x: number,
  y: number
): Phaser.GameObjects.Container => {
  if (scene.textures.exists(getRandomAssets(skins))) {
    const avatarContainer = scene.add.container(x, y);

    const skinSprite = scene.add.sprite(0, 0, getRandomAssets(skins));
    avatarContainer.add(skinSprite);

    const topSprite = scene.add.sprite(0, 0, getRandomAssets(top));
    avatarContainer.add(topSprite);

    const bottomSprite = scene.add.sprite(0, 0, getRandomAssets(bottom));
    avatarContainer.add(bottomSprite);

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
  scene.anims.create({
    key: `${randomSkin}-walk-down`,
    frames: scene.anims.generateFrameNames(randomSkin, {
      start: 1,
      end: 8,
      prefix: `${randomSkin}-walk-down-`,
      suffix: ".png",
    }),
    frameRate: 20,
    repeat: -1,
  });
};
