import { Scene } from "phaser";
import { createRandomAvatar, randomSkin } from "./Avatar";
import { controlAvatarAnimations } from "../Avatar/controlAvatar";

export class Map extends Scene {
  private avatar!: Phaser.GameObjects.Container;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys | null;

  constructor() {
    super("Map");
  }

  create() {
    const campingMap = this.make.tilemap({ key: "camping" });
    const campingGroundTilesets = campingMap.addTilesetImage(
      "camping_ground_tilesets",
      "camping_ground_tilesets"
    );
    const campingTree1Tilesets = campingMap.addTilesetImage(
      "camping_tree1_tilesets",
      "camping_tree1_tilesets"
    );

    const campingTree2Tilesets = campingMap.addTilesetImage(
      "camping_tree2_tilesets",
      "camping_tree2_tilesets"
    );

    const campingRVTilesets = campingMap.addTilesetImage(
      "camping_rv_tilesets",
      "camping_rv_tilesets"
    );

    const campingTrailerTilesets = campingMap.addTilesetImage(
      "camping_container_tilesets",
      "camping_trailer_tilesets"
    );

    const campingTentTilesets = campingMap.addTilesetImage(
      "camping_tent_tilesets",
      "camping_tent_tilesets"
    );

    const campingTreeHouseTilesets = campingMap.addTilesetImage(
      "camping_treehouse_tilesets",
      "camping_treehouse_tilesets"
    );

    const campingWaterTilesets = campingMap.addTilesetImage(
      "camping_water_tilesets",
      "camping_water_tilesets"
    );

    if (
      campingGroundTilesets &&
      campingTree1Tilesets &&
      campingTree2Tilesets &&
      campingRVTilesets &&
      campingTrailerTilesets &&
      campingTentTilesets &&
      campingTreeHouseTilesets &&
      campingWaterTilesets
    ) {
      campingMap.createLayer("camping_ground", campingGroundTilesets);
      const tree1 = campingMap.createLayer(
        "camping_tree1",
        campingTree1Tilesets
      );
      const tree2 = campingMap.createLayer(
        "camping_tree2",
        campingTree2Tilesets
      );
      const trailer = campingMap.createLayer(
        "camping_container",
        campingTrailerTilesets
      );

      const treehouse = campingMap.createLayer(
        "camping_treehouse",
        campingTreeHouseTilesets
      );
      const water = campingMap.createLayer(
        "camping_water",
        campingWaterTilesets
      );
      const tent = campingMap.createLayer("camping_tent", campingTentTilesets);
      const rv = campingMap.createLayer("camping_rv", campingRVTilesets);

      trailer?.setCollisionByProperty({ collides: true });
      tree1?.setCollisionByProperty({ collides: true });
      tree2?.setCollisionByProperty({ collides: true });
      rv?.setCollisionByProperty({ collides: true });
      treehouse?.setCollisionByProperty({ collides: true });
      water?.setCollisionByProperty({ collides: true });
      tent?.setCollisionByProperty({ collides: true });

      const debugGraphic = this.add.graphics().setAlpha(0.7);

      trailer?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      tree1?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      tree2?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      rv?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      treehouse?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      water?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });

      tent?.renderDebug(debugGraphic, {
        tileColor: null,
        collidingTileColor: new Phaser.Display.Color(243, 234, 48, 255),
        faceColor: new Phaser.Display.Color(48, 38, 37, 255),
      });
      this.avatar = createRandomAvatar(this, 550, 350);
      this.add.existing(this.avatar);
      this.avatar.setScale(2);
      this.cameras.main.startFollow(this.avatar);

      if (trailer) this.physics.add.collider(this.avatar, trailer);
      if (tent) this.physics.add.collider(this.avatar, tent);
      if (water) this.physics.add.collider(this.avatar, water);
      if (rv) this.physics.add.collider(this.avatar, rv);
      if (tree1) this.physics.add.collider(this.avatar, tree1);
      if (tree2) this.physics.add.collider(this.avatar, tree2);
      if (treehouse) this.physics.add.collider(this.avatar, treehouse);
    }

    if (this.input.keyboard) {
      this.keyboards = this.input.keyboard.createCursorKeys();
    } else {
      this.keyboards = null;
    }
  }

  update() {
    if (this.keyboards && this.avatar) {
      controlAvatarAnimations(this.avatar, this.keyboards, randomSkin);
    }
  }
}
