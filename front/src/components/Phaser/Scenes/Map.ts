import { Scene } from "phaser";
import { createRandomAvatar } from "./Avatar";

export class Map extends Scene {
  private avatar!: Phaser.Physics.Arcade.Sprite;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys;

  constructor() {
    super("Map");
  }

  create() {
    const avatar = createRandomAvatar(this, 550, 350);

    this.add.existing(avatar);

    this.cameras.main.setZoom(2);
  }

  update() {}
}
