import { Scene } from "phaser";
import { bottomAssets, skinAssets, topAssets } from "../Avatar/avatarAssets";

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

    topAssets.forEach((top) => {
      if (top.atlasUrl) {
        this.load.atlas(top.key, top.textureUrl, top.atlasUrl);
      }
    });

    bottomAssets.forEach((bottom) => {
      if (bottom.atlasUrl) {
        this.load.atlas(bottom.key, bottom.textureUrl, bottom.atlasUrl);
      }
    });
  }

  create() {
    this.scene.start("Map");
  }
}
