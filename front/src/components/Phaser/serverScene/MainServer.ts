import { Scene } from "phaser";
import { Layers } from "../../../types/server";

export class MainServer extends Scene {
  private serverInstance!: Phaser.Game;

  constructor() {
    super("MainServer");
  }

  init(data: { serverInstance: Phaser.Game }) {
    this.serverInstance = data.serverInstance;
  }

  create() {
    // 3. preload에서 tilemapTiledJSON에서 지정한 string key와 매치시켜 map으로 지정
    const map = this.make.tilemap({ key: "firstMap" });

    // 4.
    // 첫번째 param: Tiled에서 지정한 tilessets의 이름
    // 두번째 param: preload에서 지정한 이미지 파일 key
    const hillsLayerTileset = map.addTilesetImage("hills", "hillImg");
    const dirtLayerTileset = map.addTilesetImage("dirt", "dirtImg");
    const basicPlantsTileset = map.addTilesetImage(
      "basic_plants",
      "basicPlantsImg"
    );
    const grassTileset = map.addTilesetImage("grass", "grassImg");
    const waterTileset = map.addTilesetImage("water", "waterImg");
    const chickHouseTileset = map.addTilesetImage(
      "chick_house",
      "chickHouseImg"
    );
    const basicGrassTileset = map.addTilesetImage("basic_grass", "treeImg");
    const bridgeTileset = map.addTilesetImage("bridge", "bridgeImg");
    const woodenHouseTileset = map.addTilesetImage(
      "wooden_house",
      "woodenHouseImg"
    );
    const cowTileset = map.addTilesetImage("cow", "cowImg");
    const fenceTileset = map.addTilesetImage("fence", "fenceImg");
    const eggsTileset = map.addTilesetImage("eggs", "eggsImg");
    const chickenTileset = map.addTilesetImage("chicken", "chickenImg");
    const furnitureTilset = map.addTilesetImage("furniture", "furnitureImg");

    // 5. Tiled에서 지정한 Layer이름으로 해당 layer를 화면에 랜더링
    let layers: Layers = {};
    if (
      grassTileset &&
      waterTileset &&
      chickHouseTileset &&
      basicGrassTileset &&
      bridgeTileset &&
      dirtLayerTileset &&
      basicPlantsTileset &&
      hillsLayerTileset &&
      woodenHouseTileset &&
      cowTileset &&
      fenceTileset &&
      eggsTileset &&
      chickenTileset &&
      furnitureTilset
    ) {
      layers.mapLayer = map.createLayer("map", waterTileset);
      layers.groundLayer = map.createLayer("grass", grassTileset);
      layers.fenceLayer = map.createLayer("fence", fenceTileset);
      layers.chickHouseLayer = map.createLayer(
        "chick_house",
        chickHouseTileset
      );
      layers.bridgeLayer = map.createLayer("bridge", bridgeTileset);
      layers.dirtLayer = map.createLayer("dirt", dirtLayerTileset);
      layers.basicPlantsLayer = map.createLayer(
        "basic_plants",
        basicPlantsTileset
      );
      layers.hillsLayer = map.createLayer("hills", hillsLayerTileset);
      layers.woodenHouseLayer = map.createLayer("house", woodenHouseTileset);
      layers.basicGrassLayer = map.createLayer(
        "basic_grass_1",
        basicGrassTileset
      );
      layers.cowLayer = map.createLayer("cow", cowTileset);
      layers.eggsLayer = map.createLayer("eggs", eggsTileset);
      layers.chickenLayer = map.createLayer("chicken", chickenTileset);
      layers.furnitureLayer = map.createLayer("furniture", furnitureTilset);
    }

    const dragZone = this.add
      .zone(0, 0, map.widthInPixels, map.heightInPixels)
      .setOrigin(0)
      .setInteractive();

    this.input.setDraggable(dragZone);

    this.input.on(
      "drag",
      (
        pointer: Phaser.Input.Pointer,
        gameObject: Phaser.GameObjects.GameObject,
        dragX: number,
        dragY: number
      ) => {
        if (
          gameObject.input &&
          gameObject.input.dragStartX !== undefined &&
          gameObject.input.dragStartY !== undefined
        ) {
          const deltaX = dragX - gameObject.input.dragStartX;
          const deltaY = dragX - gameObject.input.dragStartY;

          Object.values(layers).forEach((layer) => {
            if (layer) {
              layer.x += deltaX;
              layer.y += deltaY;
            }
          });

          gameObject.input.dragStartX = dragX;
          gameObject.input.dragStartY = dragY;
        }
      }
    );

    this.cameras.main.setBounds(0, 0, map.widthInPixels, map.heightInPixels);

    this.input.on(
      "wheel",
      (
        pointer: Phaser.Input.Pointer,
        gameObjects: Phaser.GameObjects.GameObject[],
        deltaX: number,
        deltaY: number,
        deltaZ: number
      ) => {
        this.cameras.main.zoom = Phaser.Math.Clamp(
          this.cameras.main.zoom + deltaY * -0.001,
          0.5,
          2
        );
      }
    );

    window.addEventListener("resize", () => {
      const width = window.innerWidth;
      const height = window.innerHeight;
      this.serverInstance.scale.resize(width, height);
      this.serverInstance.canvas.style.width = width + "px";
      this.serverInstance.canvas.style.height = height + "px";
    });

    return layers;
  }
}
