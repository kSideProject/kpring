import { Scene } from "phaser";
import { createRandomAvatar } from "../Scenes/Avatar";
import { setupCameraControls } from "../Scenes/cameraControls";

export class BeachMap extends Scene {
  private avatar!: Phaser.GameObjects.Container;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys | null;
  private spaceKey!: Phaser.Input.Keyboard.Key | undefined;
  private isJumping: boolean = false;
  private nickname: string;

  constructor(nickname: string) {
    super("BeachMap");
    this.nickname = nickname;
  }

  init(data: { nickname: string }) {
    this.nickname = data.nickname;
  }

  create() {
    this.spaceKey = this.input.keyboard?.addKey(
      Phaser.Input.Keyboard.KeyCodes.SPACE
    );
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

      this.avatar = createRandomAvatar(this, 550, 350, this.nickname);
      this.cameras.main.startFollow(this.avatar);
      this.cameras.main.setZoom(2);
      this.avatar.setDepth(10);

      this.add.existing(this.avatar);
      this.avatar.setScale(2);

      if (bottomGroundLayer)
        this.physics.add.collider(this.avatar, bottomGroundLayer);
    }

    setupCameraControls(this, this.avatar);
    this.isJumping = false;

    if (this.input.keyboard) {
      this.keyboards = this.input.keyboard.createCursorKeys();
    } else {
      this.keyboards = null;
    }
  }

  update(): void {
    if (this.keyboards && this.avatar) {
      // controlAvatarAnimations(this.avatar, this.keyboards, randomSkin);
    }
  }
}
