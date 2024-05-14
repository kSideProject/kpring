import { Scene } from "phaser";

export class Preloader extends Scene {
  constructor() {
    super("Preloader");
  }

  preload() {
    this.load.image("tiles", "../assets/tiles/grass.png");
    this.load.image("house", "../assets/tiles/Free_Chicken_House.png");
    this.load.tilemapTiledJSON("grass", "../assets/tiles/TestMap.json");
    this.load.tilemapTiledJSON(
      "Free_Chicken_House",
      "../assets/tiles/TestMap.json"
    );
  }

  create() {
    this.scene.start("MainServer");
  }
}
