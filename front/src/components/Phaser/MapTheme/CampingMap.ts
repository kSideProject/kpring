import { Scene } from "phaser";
import { createRandomAvatar, randomSkin } from "../Scenes/Avatar";
import { controlAvatarAnimations } from "../Avatar/controlAvatar";

export class CampingMap extends Scene {
  private avatar!: Phaser.GameObjects.Container;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys | null;

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
        "tent_layer",
        "rv_layer",
      ];

      layers.forEach((layerName) => {
        const layer = campingMap.createLayer(layerName, campingTilesets);
        layer?.setCollisionByProperty({ collides: true });
      });

      // 애니메이션 레이어 시작
      const campfireTilesets = campingMap.addTilesetImage(
        "campfire_tilesets",
        "campfire_tilesets"
      );

      if (campfireTilesets) {
        const campfireLayer = campingMap.createLayer(
          "move_campfire_layer",
          campfireTilesets
        );
      }

      this.animatedTiles.init(campingMap);
      this.animatedTiles.start();
      // 애니메이션 레이어 끝

      this.avatar = createRandomAvatar(this, 550, 350);
      this.add.existing(this.avatar);
      this.avatar.setScale(1);
      this.cameras.main.startFollow(this.avatar);
      this.cameras.main.setZoom(2);
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
