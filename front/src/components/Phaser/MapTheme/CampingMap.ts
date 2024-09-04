import { Scene } from "phaser";
import { createRandomAvatar, randomSkin } from "../Scenes/Avatar";
import { controlAvatarAnimations } from "../Avatar/controlAvatar";

interface TileAnimationFrame {
  duration: number;
  tiledid: number;
}

interface TileData {
  animation?: TileAnimationFrame[];
}

interface TileSetData {
  [key: number]: TileData;
}

export class CampingMap extends Scene {
  private avatar!: Phaser.GameObjects.Container;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys | null;

  constructor() {
    super("CampingMap");
  }

  create() {
    let sprite: Phaser.GameObjects.Sprite;
    let messageText: Phaser.GameObjects.Text;
    let inRange = false;

    const campingMap = this.make.tilemap({ key: "camping" });

    const campingTilesets = campingMap.addTilesetImage(
      "camping_tilesets",
      "camping_tilesets"
    );

    const campfireTilesets = campingMap.addTilesetImage(
      "campfire_tilesets",
      "campfire_tilesets"
    );

    if (campingTilesets && campfireTilesets) {
      const bottomGroundLayer = campingMap.createLayer(
        "bottom_ground_layer",
        campingTilesets
      );

      const topGroundLayer = campingMap.createLayer(
        "top_ground_layer",
        campingTilesets
      );

      const bottomWaterLayer = campingMap.createLayer(
        "bottom_water_layer",
        campingTilesets
      );

      const topWaterLayer = campingMap.createLayer(
        "top_water_layer",
        campingTilesets
      );

      const bottomTreeLayer = campingMap.createLayer(
        "bottom_tree_layer",
        campingTilesets
      );

      const midTreeLayer = campingMap.createLayer(
        "mid_tree_layer",
        campingTilesets
      );

      const topTreeLayer = campingMap.createLayer(
        "top_tree_layer",
        campingTilesets
      );

      const bottomTrailerLayer = campingMap.createLayer(
        "bottom_trailer_layer",
        campingTilesets
      );

      const topTrailerLayer = campingMap.createLayer(
        "top_trailer_layer",
        campingTilesets
      );

      const bottomObjectsLayer = campingMap.createLayer(
        "bottom_objects_layer",
        campingTilesets
      );

      const topObjectsLayer = campingMap.createLayer(
        "top_objects_layer",
        campingTilesets
      );

      const tentLayer = campingMap.createLayer("tent_layer", campingTilesets);
      const rvLayer = campingMap.createLayer("rv_layer", campingTilesets);

      // 애니메이션 들어간 레이어들
      const moveCampfireLayer = campingMap.createLayer(
        "move_campfire_layer",
        campfireTilesets
      );

      topGroundLayer?.setCollisionByProperty({ collides: true });
      bottomWaterLayer?.setCollisionByProperty({ collides: true });
      topWaterLayer?.setCollisionByProperty({ collides: true });
      bottomTreeLayer?.setCollisionByProperty({ collides: true });
      midTreeLayer?.setCollisionByProperty({ collides: true });
      topTreeLayer?.setCollisionByProperty({ collides: true });
      bottomTrailerLayer?.setCollisionByProperty({ collides: true });
      topTrailerLayer?.setCollisionByProperty({ collides: true });
      bottomObjectsLayer?.setCollisionByProperty({ collides: true });
      topObjectsLayer?.setCollisionByProperty({ collides: true });
      tentLayer?.setCollisionByProperty({ collides: true });
      rvLayer?.setCollisionByProperty({ collides: true });

      const debugGraphic = this.add.graphics().setAlpha(0.7);

      bottomGroundLayer?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      this.avatar = createRandomAvatar(this, 550, 350);
      this.add.existing(this.avatar);
      this.avatar.setScale(1);
      this.cameras.main.startFollow(this.avatar);
      this.cameras.main.setZoom(2);

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
