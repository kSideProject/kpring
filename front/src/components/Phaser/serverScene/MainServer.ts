import { GameObjects, Scene } from "phaser";
import { EventBus } from "../EventBus";

export class MainServer extends Scene {
  constructor() {
    super("MainServer");
  }

  create() {
    const map = this.make.tilemap({ key: "grass" });
    const tileset = map.addTilesetImage("grass", "tiles");
    const house = map.addTilesetImage("Free_Chicken_House", "tiles");

    if (tileset && house) {
      map.createLayer("Grass", tileset);
      map.createLayer("House", house);
    } else {
      console.error("error");
    }
  }
}
