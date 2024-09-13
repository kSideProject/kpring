import "phaser";
import AnimatedTiles from "phaser-animated-tiles-phaser3.5";

declare module "phaser" {
  interface Scene {
    animatedTiles: AnimatedTiles;
  }
}
