import { Scene } from "phaser";

export const playerJumpAction = (
  scene: Scene,
  avatar: Phaser.GameObjects.Container
) => {
  const skinData = (avatar as any).skinData;
  const jumpTextureKey = `${skinData.key}-jump`;

  console.log(jumpTextureKey);
};

export default playerJumpAction;
