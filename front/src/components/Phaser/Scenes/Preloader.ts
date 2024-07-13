import { Scene } from "phaser";
import { skinAssets } from "../Avatar/avatarAssets";

export class Preloader extends Scene {
  constructor() {
    super("Preloader");
  }

  preload() {
    skinAssets.forEach((skin) => {
      if (skin.atlasUrl) {
        this.load.atlas(skin.key, skin.textureUrl, skin.atlasUrl);
      }
    });

    this.load.json(
      "top-costumes",
      "../assets/avatar/walk/costumes/top/top1-walk.json"
    );
    this.load.atlas(
      "top-costume-texture",
      "../assets/avatar/walk/costumes/top/top1-walk.png",
      "../assets/avatar/walk/costumes/top/top1-walk.json"
    );
  }

  create() {
    this.scene.start("Map");
  }
}
