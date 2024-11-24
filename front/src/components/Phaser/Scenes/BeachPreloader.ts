import { Scene } from "phaser";
import { skinAssets } from "../Avatar/avatarAssets";

export class BeachPreloader extends Scene {
  constructor() {
    super("Preloader");
  }

  preload() {
    // Avatar
    skinAssets.forEach((skin) => {
      if (skin.atlasUrl) {
        this.load.atlas(skin.key, skin.textureUrl, skin.atlasUrl);
      }
    });

    this.load.atlas(
      "top-costume-texture",
      "../assets/avatar/walk/costumes/top/top1-walk.png",
      "../assets/avatar/walk/costumes/top/top1-walk.json"
    );

    this.load.atlas(
      "bottom-costume-texture",
      "../assets/avatar/walk/costumes/bottom/bottom1-walk.png",
      "../assets/avatar/walk/costumes/bottom/bottom1-walk.json"
    );

    this.load.atlas(
      "hair-texture",
      "../assets/avatar/walk/hair/hair1.png",
      "../assets/avatar/walk/hair/hair1.json"
    );

    // Map
    this.load.image("beach_tilesets", "../assets/map/beach/beach_tilesets.png");
    this.load.tilemapTiledJSON("beach", "../assets/map/beach/beach.json");
  }

  create() {
    this.scene.start("BeachMap");
  }
}
