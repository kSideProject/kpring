import { Physics } from "phaser";

export const controlAvatarAnimations = (
  avatar: Phaser.GameObjects.Container,
  keyboards: Phaser.Types.Input.Keyboard.CursorKeys,
  skinKey: string,
  spaceKey: Phaser.Input.Keyboard.Key | undefined
) => {
  const skinSprite = avatar?.list[0] as Phaser.GameObjects.Sprite | null;
  const topSprite = avatar?.list[1] as Phaser.GameObjects.Sprite | null;
  const bottomSprite = avatar?.list[2] as Phaser.GameObjects.Sprite | null;
  const hairSprite = avatar.list[3] as Phaser.GameObjects.Sprite | null;
  const body = avatar.body as Physics.Arcade.Body;
  body.setVelocity(0);

  type Direction = "left" | "right" | "up" | "down";
  let facingDirection: Direction = "down";
  let isJumping = false;

  if (!skinSprite || !topSprite || !bottomSprite || !hairSprite) {
    return;
  }

  // 점프 동작 처리
  if (spaceKey?.isDown && !isJumping) {
    isJumping = true;

    // 점프 시 방향에 맞춰 이동 속도
    if (keyboards.left?.isDown) {
      body.setVelocityX(-100);
      facingDirection = "left";
      skinSprite.anims.play(`${skinKey}-jump-left`, true);
      topSprite.anims.play("top-jump-left", true);
      bottomSprite.anims.play("bottom-jump-left", true);
      hairSprite.anims.play("hair-jump-left", true);
    } else if (keyboards.right?.isDown) {
      body.setVelocityX(100);
      facingDirection = "right";
      skinSprite.anims.play(`${skinKey}-jump-right`, true);
      topSprite.anims.play("top-jump-right", true);
      bottomSprite.anims.play("bottom-jump-right", true);
      hairSprite.anims.play("hair-jump-right", true);
    } else if (keyboards.up?.isDown) {
      body.setVelocityY(-100);
      facingDirection = "up";
      skinSprite.anims.play(`${skinKey}-jump-up`, true);
      topSprite.anims.play("top-jump-back", true);
      bottomSprite.anims.play("bottom-jump-back", true);
      hairSprite.anims.play("hair-jump-back", true);
    } else if (keyboards.down?.isDown) {
      body.setVelocityY(100);
      facingDirection = "down";
      skinSprite.anims.play(`${skinKey}-jump-down`, true);
      topSprite.anims.play("top-jump-front", true);
      bottomSprite.anims.play("bottom-jump-front", true);
      hairSprite.anims.play("hair-jump-front", true);
    } else {
      body.setVelocity(0);
      facingDirection = "down";
      skinSprite.anims.play(`${skinKey}-jump-down`, true);
      topSprite.anims.play("top-jump-front", true);
      bottomSprite.anims.play("bottom-jump-front", true);
      hairSprite.anims.play("hair-jump-front", true);
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
    } else if (keyboards.down?.isDown) {
      facingDirection = "down";
      body.setVelocityY(100);
      skinSprite.anims.play(`${skinKey}-walk-down`, true);
      topSprite.anims.play("top-walk-front", true);
      bottomSprite.anims.play("bottom-walk-front", true);
      hairSprite.anims.play("hair-walk-front", true);
    } else {
      body.setVelocity(0);
      body.stop();
      skinSprite.anims.stop();
      topSprite.anims.stop();
      bottomSprite.anims.stop();
      hairSprite.anims.stop();
    }
  }
};
