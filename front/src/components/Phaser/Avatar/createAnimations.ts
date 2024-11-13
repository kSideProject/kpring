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
  // === 걸을 때 ===
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

  // === 점프할 때 ===
  // 피부색
  scene.anims.create({
    key: `${skinKey}-jump-left`,
    frames: scene.anims.generateFrameNames(`${skinKey}-jump`, {
      prefix: `${skinKey}-jump-left-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `${skinKey}-jump-right`,
    frames: scene.anims.generateFrameNames(`${skinKey}-jump`, {
      prefix: `${skinKey}-jump-right-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `${skinKey}-jump-down`,
    frames: scene.anims.generateFrameNames(`${skinKey}-jump`, {
      prefix: `${skinKey}-jump-down-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `${skinKey}-jump-up`,
    frames: scene.anims.generateFrameNames(`${skinKey}-jump`, {
      prefix: `${skinKey}-jump-up-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  // 상의
  scene.anims.create({
    key: `top-jump-front`,
    frames: scene.anims.generateFrameNames("top-costume-jump-texture", {
      prefix: `${topKey}-${topColorKey}-jump-front-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `top-jump-back`,
    frames: scene.anims.generateFrameNames("top-costume-jump-texture", {
      prefix: `${topKey}-${topColorKey}-jump-back-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `top-jump-right`,
    frames: scene.anims.generateFrameNames("top-costume-jump-texture", {
      prefix: `${topKey}-${topColorKey}-jump-right-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `top-jump-left`,
    frames: scene.anims.generateFrameNames("top-costume-jump-texture", {
      prefix: `${topKey}-${topColorKey}-jump-left-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  // 하의
  scene.anims.create({
    key: `bottom-jump-front`,
    frames: scene.anims.generateFrameNames("bottom-costume-jump-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-jump-front-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `bottom-jump-back`,
    frames: scene.anims.generateFrameNames("bottom-costume-jump-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-jump-back-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `bottom-jump-left`,
    frames: scene.anims.generateFrameNames("bottom-costume-jump-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-jump-left-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });

  scene.anims.create({
    key: `bottom-jump-right`,
    frames: scene.anims.generateFrameNames("bottom-costume-jump-texture", {
      prefix: `${bottomKey}-${bottomColorKey}-jump-right-`,
      start: 1,
      end: 5,
      suffix: ".png",
    }),
    frameRate: frameRate,
    repeat: -1,
  });
};
