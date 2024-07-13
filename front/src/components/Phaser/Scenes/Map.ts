import { Scene } from "phaser";
import { avatarAnimation, createRandomAvatar, randomSkin } from "./Avatar";

export class Map extends Scene {
  private avatar!: Phaser.Physics.Arcade.Sprite;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys;

  constructor() {
    super("Map");
  }

  create() {
    const avatar = createRandomAvatar(this, 450, 450);

    this.add.existing(avatar);

    avatarAnimation(this);

    this.cameras.main.setZoom(3);
  }

  update() {}
}
