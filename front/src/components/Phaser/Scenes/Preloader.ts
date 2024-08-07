import { Scene } from "phaser";
import { skinAssets } from "../Avatar/avatarAssets";

export class Preloader extends Scene {
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
    this.load.image(
      "camping_ground_tilesets",
      "../assets/map/camping/ground_tilesets.png"
    );
    this.load.tilemapTiledJSON("camping", "../assets/map/camping/camping.json");
  }

  create() {
    this.scene.start("Map");
  }
}
