import { Scene } from "phaser";
import { createRandomAvatar, randomSkin } from "../Scenes/Avatar";
import { controlAvatarAnimations } from "../Avatar/controlAvatar";

export class BeachMap extends Scene {
  private avatar!: Phaser.GameObjects.Container;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys | null;

  constructor() {
    super("BeachMap");
  }

  create() {
    const beachMap = this.make.tilemap({ key: "beach" });
    const beachTilesets = beachMap.addTilesetImage(
      "beach_tilesets",
      "beach_tilesets"
    );

    if (beachTilesets) {
      const bottomGroundLayer = beachMap.createLayer(
        "bottom_ground_layer",
        beachTilesets
      );

      bottomGroundLayer?.setCollisionByProperty({ collides: true });

      const debugGraphic = this.add.graphics().setAlpha(0.7);

      bottomGroundLayer?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      this.avatar = createRandomAvatar(this, 550, 350);
      this.add.existing(this.avatar);
      this.avatar.setScale(2);
      this.cameras.main.startFollow(this.avatar);

      if (bottomGroundLayer)
        this.physics.add.collider(this.avatar, bottomGroundLayer);
    }

    if (this.input.keyboard) {
      this.keyboards = this.input.keyboard.createCursorKeys();
    } else {
      this.keyboards = null;
    }
  }

  update(): void {
    if (this.keyboards && this.avatar) {
      controlAvatarAnimations(this.avatar, this.keyboards, randomSkin);
    }
  }
}
