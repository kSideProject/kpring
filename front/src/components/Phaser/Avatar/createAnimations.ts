import { Scene } from "phaser";

export const createAnimations = (
  scene: Scene,
  skinKey: string,
  topKey: string,
  topColorKey: string,
  bottomKey: string,
  bottomColorKey: string,
  hairKey: string,
  hairColorKey: string
): void => {
  // 피부색
  const frameRate = 20;

  scene.anims.create({
    key: `${skinKey}-walk-left`,
    frames: scene.anims.generateFrameNames(skinKey, {
      prefix: `${skinKey}-walk-left-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `${skinKey}-walk-up`,
    frames: scene.anims.generateFrameNames(skinKey, {
      prefix: `${skinKey}-walk-up-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `${skinKey}-walk-down`,
    frames: scene.anims.generateFrameNames(skinKey, {
      prefix: `${skinKey}-walk-down-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `${skinKey}-walk-right`,
    frames: scene.anims.generateFrameNames(skinKey, {
      prefix: `${skinKey}-walk-right-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  // 상의
  scene.anims.create({
    key: `top-walk-front`,
    frames: scene.anims.generateFrameNames("top-costume-texture", {
      prefix: `${topKey}-${topColorKey}-walk-front-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `top-walk-back`,
    frames: scene.anims.generateFrameNames("top-costume-texture", {
      prefix: `${topKey}-${topColorKey}-walk-back-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `top-walk-left`,
    frames: scene.anims.generateFrameNames("top-costume-texture", {
      prefix: `${topKey}-${topColorKey}-walk-left-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `top-walk-right`,
    frames: scene.anims.generateFrameNames("top-costume-texture", {
      prefix: `${topKey}-${topColorKey}-walk-right-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  // 하의
  scene.anims.create({
    key: `bottom-walk-front`,
    frames: scene.anims.generateFrameNames("bottom-costume-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-walk-front-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `bottom-walk-back`,
    frames: scene.anims.generateFrameNames("bottom-costume-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-walk-back-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `bottom-walk-left`,
    frames: scene.anims.generateFrameNames("bottom-costume-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-walk-left-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `bottom-walk-right`,
    frames: scene.anims.generateFrameNames("bottom-costume-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-walk-right-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  // 헤어
  scene.anims.create({
    key: `hair-walk-front`,
    frames: scene.anims.generateFrameNames("hair-texture", {
      prefix: `${hairKey}-${hairColorKey}-walk-front-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `hair-walk-back`,
    frames: scene.anims.generateFrameNames("hair-texture", {
      prefix: `${hairKey}-${hairColorKey}-walk-back-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `hair-walk-left`,
    frames: scene.anims.generateFrameNames("hair-texture", {
      prefix: `${hairKey}-${hairColorKey}-walk-left-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `hair-walk-right`,
    frames: scene.anims.generateFrameNames("hair-texture", {
      prefix: `${hairKey}-${hairColorKey}-walk-right-`,
      start: 1,
      end: 8,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });
};
