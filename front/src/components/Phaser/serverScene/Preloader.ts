import { Scene } from "phaser";

export class Preloader extends Scene {
  constructor() {
    super("Preloader");
  }

  preload() {
    // 1. assets폴더에서 사용할 png파일을 모두 불러온다.
    this.load.image("grassImg", "../assets/grass.png");
    this.load.image("waterImg", "../assets/water.png");
    this.load.image("bridgeImg", "../assets/bridge.png");
    this.load.image("chickHouseImg", "../assets/chick_house.png");
    this.load.image("treeImg", "../assets/trees.png");
    this.load.image("dirtImg", "../assets/dirt.png");
    this.load.image("basicPlantsImg", "../assets/basic_plants.png");
    this.load.image("hillImg", "../assets/hills.png");
    this.load.image("woodenHouseImg", "../assets/wooden_house.png");
    this.load.image("basicGrassImg", "../assets/basic_grass.png");
    this.load.image("cowImg", "../assets/cow.png");
    this.load.image("fenceImg", "../assets/fences.png");
    this.load.image("chickenImg", "../assets/chicken.png");
    this.load.image("eggsImg", "../assets/eggs.png");
    this.load.image("furnitureImg", "../assets/furniture.png");

    // 2. Tiled에서 작업하고 JSON파일 저장한 후 불러온다.
    this.load.tilemapTiledJSON("firstMap", "../assets/firstMap.json");
  }

  create() {
    this.scene.start("MainServer");
  }
}
