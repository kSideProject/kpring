import { Scene } from "phaser";
import { createRandomAvatar, randomSkin } from "../Scenes/Avatar";
import { controlAvatarAnimations } from "../Avatar/controlAvatar";
import { setupCameraControls } from "../Scenes/cameraControls";

export class CampingMap extends Scene {
  private avatar!: Phaser.GameObjects.Container;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys | null;
  private spaceKey!: Phaser.Input.Keyboard.Key | undefined;
  private chatText!: Phaser.GameObjects.Text | null;
  private isJumping: boolean = false;
  private nickname: string = "";
  private boundHandleChatMessage: (event: Event) => void;

  constructor(nickname: string) {
    super("CampingMap");
    this.nickname = nickname;
    this.boundHandleChatMessage = this.handleChatMessage.bind(this);
  }

  handleChatMessage = (event: Event) => {
    const customEvent = event as CustomEvent<string>;
    const message = customEvent.detail;

    if (!this.scene.isActive() || !this.avatar) {
      return;
    }

    if (this.chatText) {
      this.chatText.destroy();
    }

    this.chatText = this.add.text(this.avatar.x, this.avatar.y - 50, message, {
      font: "14px Arial",
      color: "#ffffff",
      backgroundColor: "#000000",
      padding: { x: 10, y: 5 },
    });

    this.chatText.setOrigin(0.5, 1);

    this.time.delayedCall(3000, () => {
      if (this.chatText) {
        this.chatText.destroy();
        this.chatText = null;
      }
    });
  };

  init(data: { nickname: string }) {
    this.nickname = data.nickname || "Guest";
  }

  create() {
    window.addEventListener("chatMessage", this.boundHandleChatMessage);
    // this.avatar = this.add.container(300, 300);
    this.spaceKey = this.input.keyboard?.addKey(
      Phaser.Input.Keyboard.KeyCodes.SPACE
    );
    const campingMap = this.make.tilemap({ key: "camping" });
    const campingTilesets = campingMap.addTilesetImage(
      "camping_tilesets",
      "camping_tilesets"
    );
    if (campingTilesets) {
      const layers = [
        "bottom_ground_layer",
        "mid_ground_layer",
        "top_ground_layer",
        "bottom_water_layer",
        "top_water_layer",
        "bottom_tree_layer",
        "mid_tree_layer",
        "top_tree_layer",
        "bottom_trailer_layer",
        "top_trailer_layer",
        "bottom_objects_layer",
        "top_objects_layer",
        "bottom_bridge_layer",
        "tent_layer",
        "rv_layer",
      ];

      this.avatar = createRandomAvatar(this, 520, 350, this.nickname);
      this.add.existing(this.avatar);

      this.cameras.main.startFollow(this.avatar);
      this.cameras.main.setZoom(2);
      this.avatar.setDepth(10);

      layers.forEach((layerName) => {
        const layer = campingMap.createLayer(layerName, campingTilesets);

        if (layer) {
          layer?.setCollisionByProperty({ collides: true });
          this.physics.add.collider(this.avatar, layer);
        }

        const debugGraphic = this.add.graphics().setAlpha(0.7);
        layer?.renderDebug(debugGraphic, {
          tileColor: null,
          collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
          faceColor: new Phaser.Display.Color(48, 38, 37, 255),
        });
      });

      // 애니메이션 레이어 시작
      const campfireTilesets = campingMap.addTilesetImage(
        "campfire_tilesets",
        "campfire_tilesets"
      );

      const campfire1Tilesets = campingMap.addTilesetImage(
        "campfire1_tilesets",
        "campfire1_tilesets"
      );

      const waterfallTilesets = campingMap.addTilesetImage(
        "waterfall_tilesets",
        "waterfall_tilesets"
      );

      const boatTilesets = campingMap.addTilesetImage(
        "boat_tilesets",
        "boat_tilesets"
      );

      const fishingBoatATilesets = campingMap.addTilesetImage(
        "fishing_boat_tilesets",
        "fishing_boat_tilesets_A"
      );

      const fishes1ATilesets = campingMap.addTilesetImage(
        "fishes1_tilesets",
        "fishes1_tilesets"
      );

      if (
        campfireTilesets &&
        campfire1Tilesets &&
        waterfallTilesets &&
        boatTilesets &&
        fishingBoatATilesets &&
        fishes1ATilesets
      ) {
        campingMap.createLayer("move_campfire_layer", campfireTilesets);
        campingMap.createLayer("move_campfire1_layer", campfire1Tilesets);
        campingMap.createLayer("move_waterfall_layer", waterfallTilesets);
        campingMap.createLayer("move_boat_layer", boatTilesets);
        campingMap.createLayer(
          "move_fishing_boat_layer_A",
          fishingBoatATilesets
        );
        campingMap.createLayer("move_fishes1_layer", fishes1ATilesets);
      }

      this.animatedTiles.init(campingMap);
      this.animatedTiles.start();
      // 애니메이션 레이어 끝
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
      controlAvatarAnimations(
        this.avatar,
        this.keyboards,
        randomSkin,
        this.spaceKey
      );
    }
  }

  shutdown() {
    window.removeEventListener("chatMessage", this.boundHandleChatMessage);
  }
}
