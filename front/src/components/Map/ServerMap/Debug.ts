// 이 파일은 캐릭터와 물체의 충돌을 확인하기 위한 디버그 파일입니다.

import { Scene } from "phaser";
import { Layers } from "../../../types/map";

export const debugCollision = (scene: Scene, layers: Layers) => {
  const collidableLayers = [
    layers.hillsCollidesLayer,
    layers.groundLayer,
    layers.fenceLayer,
    layers.furnitureLayer,
    layers.woodenHouseLayer,
    layers.wallsLayer,
  ];

  collidableLayers.forEach((layer) => {
    layer?.setCollisionByProperty({ collides: true });
  });

  const debugGraphics = scene.add.graphics().setAlpha(0.7);
  renderDebug(layers, debugGraphics);
};

export const renderDebug = (
  layers: Layers,
  debugGraphics: Phaser.GameObjects.Graphics
) => {
  const debugOptions = {
    tileColor: null,
    collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
    faceColor: new Phaser.Display.Color(40, 39, 37, 255),
  };

  layers.groundLayer?.renderDebug(debugGraphics, debugOptions);
  layers.fenceLayer?.renderDebug(debugGraphics, debugOptions);
  layers.furnitureLayer?.renderDebug(debugGraphics, debugOptions);
  layers.woodenHouseLayer?.renderDebug(debugGraphics, debugOptions);
  layers.hillsCollidesLayer?.renderDebug(debugGraphics, debugOptions);
};

export const addCollider = (
  scene: Scene,
  character: Phaser.Physics.Arcade.Sprite,
  layers: Layers
) => {
  const collidableLayers = [
    layers.hillsCollidesLayer,
    layers.groundLayer,
    layers.fenceLayer,
    layers.furnitureLayer,
    layers.woodenHouseLayer,
  ];

  collidableLayers.forEach((layer) => {
    if (layer) {
      scene.physics.add.collider(
        character,
        layer as Phaser.Tilemaps.TilemapLayer
      );
    }
  });
};
