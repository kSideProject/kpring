import { Scene } from "phaser";

interface Layers {
  mapLayer?: Phaser.Tilemaps.TilemapLayer | null;
  groundLayer?: Phaser.Tilemaps.TilemapLayer | null;
  chickHouseLayer?: Phaser.Tilemaps.TilemapLayer | null;
  bridgeLayer?: Phaser.Tilemaps.TilemapLayer | null;
  dirtLayer?: Phaser.Tilemaps.TilemapLayer | null;
  basicPlantsLayer?: Phaser.Tilemaps.TilemapLayer | null;
  hillsLayer?: Phaser.Tilemaps.TilemapLayer | null;
  woodenHouseLayer?: Phaser.Tilemaps.TilemapLayer | null;
  basicGrassLayer?: Phaser.Tilemaps.TilemapLayer | null;
  cowLayer?: Phaser.Tilemaps.TilemapLayer | null;
  fenceLayer?: Phaser.Tilemaps.TilemapLayer | null;
  eggsLayer?: Phaser.Tilemaps.TilemapLayer | null;
  chickenLayer?: Phaser.Tilemaps.TilemapLayer | null;
  furnitureLayer?: Phaser.Tilemaps.TilemapLayer | null;
}

export class MainServer extends Scene {
  constructor() {
    super("MainServer");
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

    return layers;
  }
}
