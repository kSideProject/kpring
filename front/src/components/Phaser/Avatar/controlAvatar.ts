import { Physics } from "phaser";

export const controlAvatarAnimations = (
  avatar: Phaser.GameObjects.Container,
  keyboards: Phaser.Types.Input.Keyboard.CursorKeys,
  skinKey: string
) => {
  const skinSprite = avatar?.list[0] as Phaser.GameObjects.Sprite | null;
  const topSprite = avatar?.list[1] as Phaser.GameObjects.Sprite | null;
  const bottomSprite = avatar?.list[2] as Phaser.GameObjects.Sprite | null;
  const hairSprite = avatar.list[3] as Phaser.GameObjects.Sprite | null;
  const body = avatar.body as Physics.Arcade.Body;
  body.setVelocity(0);

  if (!skinSprite || !topSprite || !bottomSprite || !hairSprite) {
    return;
  }

  if (keyboards.left?.isDown) {
    body.setVelocityX(-100);
    skinSprite.anims.play(`${skinKey}-walk-left`, true);
    topSprite.anims.play("top-walk-left", true);
    bottomSprite.anims.play("bottom-walk-left", true);
    hairSprite.anims.play("hair-walk-left", true);
  } else if (keyboards.right?.isDown) {
    body.setVelocityX(100);
    skinSprite.anims.play(`${skinKey}-walk-right`, true);
    topSprite.anims.play("top-walk-right", true);
    bottomSprite.anims.play("bottom-walk-right", true);
    hairSprite.anims.play("hair-walk-right", true);
  } else if (keyboards.up?.isDown) {
    body.setVelocityY(-100);
    skinSprite.anims.play(`${skinKey}-walk-up`, true);
    topSprite.anims.play("top-walk-back", true);
    bottomSprite.anims.play("bottom-walk-back", true);
    hairSprite.anims.play("hair-walk-back", true);
  } else if (keyboards.down.isDown) {
    body.setVelocityY(100);
    skinSprite.anims.play(`${skinKey}-walk-down`, true);
    topSprite.anims.play("top-walk-front", true);
    bottomSprite.anims.play("bottom-walk-front", true);
    hairSprite.anims.play("hair-walk-front", true);
  } else {
    body.stop();
    skinSprite.anims.stop();
    topSprite.anims.stop();
    bottomSprite.anims.stop();
    hairSprite.anims.stop();
  }
};
