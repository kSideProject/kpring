import { GameObjects, Scene } from "phaser";
import { createRandomAvatar, randomSkin } from "../Scenes/Avatar";
import { controlAvatarAnimations } from "../Avatar/controlAvatar";
import { setupCameraControls } from "../Scenes/cameraControls";

export class CampingMap extends Scene {
  private avatar!: Phaser.GameObjects.Container;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys | null;
  private isDragging: boolean = false;
  private dragStartPoint = new Phaser.Math.Vector2();

  constructor() {
    super("CampingMap");
  }

  create() {
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

      this.avatar = createRandomAvatar(this, 520, 350);
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
