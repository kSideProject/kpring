import { Scene } from "phaser";

interface Layers {
  mapLayer?: Phaser.Tilemaps.TilemapLayer | null;
  groundLayer?: Phaser.Tilemaps.TilemapLayer | null;
  chickHouseLayer?: Phaser.Tilemaps.TilemapLayer | null;
  treeLayer?: Phaser.Tilemaps.TilemapLayer | null;
  bridgeLayer?: Phaser.Tilemaps.TilemapLayer | null;
  dirtLayer?: Phaser.Tilemaps.TilemapLayer | null;
  basicPlantsLayer?: Phaser.Tilemaps.TilemapLayer | null;
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
    const treeTileset = map.addTilesetImage("basic_grass", "treeImg");
    const bridgeTileset = map.addTilesetImage("bridge", "bridgeImg");

    // 5. Tiled에서 지정한 Layer이름으로 해당 layer를 화면에 랜더링
    let layers: Layers = {};
    if (
      grassTileset &&
      waterTileset &&
      chickHouseTileset &&
      treeTileset &&
      bridgeTileset &&
      dirtLayerTileset &&
      basicPlantsTileset
    ) {
      layers.mapLayer = map.createLayer("map", waterTileset);
      layers.groundLayer = map.createLayer("grass", grassTileset);
      layers.treeLayer = map.createLayer("trees", treeTileset);
      layers.chickHouseLayer = map.createLayer("house", chickHouseTileset);
      layers.bridgeLayer = map.createLayer("bridge", bridgeTileset);
      layers.dirtLayer = map.createLayer("ground", dirtLayerTileset);
      layers.basicPlantsLayer = map.createLayer("plants", basicPlantsTileset);
    }

    return layers;
  }
}
