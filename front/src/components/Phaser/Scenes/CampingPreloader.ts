import { Scene } from "phaser";
import { skinAssets } from "../Avatar/avatarAssets";

export class CampingPreloader extends Scene {
  constructor() {
    super("Preloader");
  }

  preload() {
    // Avatar
    skinAssets.forEach((skin) => {
      // 걸을 때, 애니메이션 이미지 파일 및 json파일 프리로드
      if (skin.atlasUrl) {
        this.load.atlas(skin.key, skin.textureUrl, skin.atlasUrl);
      }

      // 점프할 때, 애니메이션 이미지 파일 및 json파일 프리로드
      if (skin.jumpTextureUrl && skin.jumpAtlasUrl) {
        this.load.atlas(
          `${skin.key}-jump`,
          skin.jumpTextureUrl,
          skin.jumpAtlasUrl
        );
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
      "camping_tilesets",
      "../assets/map/camping/camping_tilesets.png"
    );

    this.load.spritesheet(
      "campfire_tilesets",
      "../assets/map/camping/campfire_tilesets.png",
      {
        frameWidth: 32,
        frameHeight: 32,
      }
    );

    this.load.spritesheet(
      "campfire1_tilesets",
      "../assets/map/camping/campfire1_tilesets.png",
      {
        frameWidth: 32,
        frameHeight: 32,
      }
    );

    this.load.spritesheet(
      "boat_tilesets",
      "../assets/map/camping/boat_tilesets.png",
      {
        frameWidth: 32,
        frameHeight: 32,
      }
    );

    this.load.spritesheet(
      "waterfall_tilesets",
      "../assets/map/camping/waterfall_tilesets.png",
      {
        frameWidth: 32,
        frameHeight: 64,
      }
    );

    this.load.spritesheet(
      "fishing_boat_tilesets_A",
      "../assets/map/camping/fishing_boat_tilesets_A.png",
      {
        frameWidth: 64,
        frameHeight: 52,
      }
    );

    this.load.spritesheet(
      "fishes_tilesets",
      "../assets/map/camping/fishes_tilesets.png",
      {
        frameWidth: 32,
        frameHeight: 32,
      }
    );

    this.load.spritesheet(
      "fishes1_tilesets",
      "../assets/map/camping/fishes1_tilesets.png",
      {
        frameWidth: 32,
        frameHeight: 32,
      }
    );

    this.load.tilemapTiledJSON("camping", "../assets/map/camping/camping.json");
  }

  create() {
    this.scene.start("CampingMap");
  }
}
