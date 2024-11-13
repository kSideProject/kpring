import { Physics } from "phaser";

export const controlAvatarAnimations = (
  avatar: Phaser.GameObjects.Container,
  keyboards: Phaser.Types.Input.Keyboard.CursorKeys,
  skinKey: string,
  spaceKey: Phaser.Input.Keyboard.Key | undefined,
  isJumping: boolean
) => {
  const skinSprite = avatar?.list[0] as Phaser.GameObjects.Sprite | null;
  const topSprite = avatar?.list[1] as Phaser.GameObjects.Sprite | null;
  const bottomSprite = avatar?.list[2] as Phaser.GameObjects.Sprite | null;
  const hairSprite = avatar.list[3] as Phaser.GameObjects.Sprite | null;
  const body = avatar.body as Physics.Arcade.Body;
  body.setVelocity(0);

  type Direction = "left" | "right" | "up" | "down";
  let facingDirection: Direction = "down";

  if (!skinSprite || !topSprite || !bottomSprite || !hairSprite) {
    return;
  }

  if (spaceKey?.isDown && !isJumping) {
    isJumping = true;

    if (facingDirection === ("left" as Direction)) {
      skinSprite.anims.play(`${skinKey}-jump-left`, true);
    } else if (facingDirection === ("right" as Direction)) {
      skinSprite.anims.play(`${skinKey}-jump-right`, true);
    } else if (facingDirection === ("up" as Direction)) {
      skinSprite.anims.play(`${skinKey}-jump-up`, true);
    } else if (facingDirection === "down") {
      skinSprite.anims.play(`${skinKey}-jump-down`, true);
    }
    // 점프 애니메이션 종료 후 점프 상태 초기화
    skinSprite.once("animationcomplete", () => {
      isJumping = false;
    });
  } else if (!isJumping) {
    if (keyboards.left?.isDown) {
      facingDirection = "left";
      body.setVelocityX(-100);
      skinSprite.anims.play(`${skinKey}-walk-left`, true);
      topSprite.anims.play("top-walk-left", true);
      bottomSprite.anims.play("bottom-walk-left", true);
      hairSprite.anims.play("hair-walk-left", true);
    } else if (keyboards.right?.isDown) {
      facingDirection = "right";
      body.setVelocityX(100);
      skinSprite.anims.play(`${skinKey}-walk-right`, true);
      topSprite.anims.play("top-walk-right", true);
      bottomSprite.anims.play("bottom-walk-right", true);
      hairSprite.anims.play("hair-walk-right", true);
    } else if (keyboards.up?.isDown) {
      facingDirection = "up";
      body.setVelocityY(-100);
      skinSprite.anims.play(`${skinKey}-walk-up`, true);
      topSprite.anims.play("top-walk-back", true);
      bottomSprite.anims.play("bottom-walk-back", true);
      hairSprite.anims.play("hair-walk-back", true);
    } else if (keyboards.down.isDown) {
      facingDirection = "down";
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
  }
};
