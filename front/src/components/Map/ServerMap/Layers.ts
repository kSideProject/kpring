import { Scene } from "phaser";
import { Layers } from "../../../types/server";

export const createLayers = (map: Phaser.Tilemaps.Tilemap, scene: Scene) => {
  // Tiled에서 그린 잔디, 집, 나무 등과 같은 타일 요소들을 화면에 뿌려준다.
  // preload에서 지정한 이미지 파일 keymap.addTilesetImage(Tiled에서 지정한 tilessets의 이름, preload에서 지정한 이미지 파일 key);
  const hillsLayerTileset = map.addTilesetImage("hills", "hillImg");
  const dirtLayerTileset = map.addTilesetImage("dirt", "dirtImg");
  const basicPlantsTileset = map.addTilesetImage(
    "basic_plants",
    "basicPlantsImg"
  );
  const grassTileset = map.addTilesetImage("grass", "grassImg");
  const waterTileset = map.addTilesetImage("water", "waterImg");
  const furnitureTilset = map.addTilesetImage("furniture", "furnitureImg");

  const chickHouseTileset = map.addTilesetImage("chick_house", "chickHouseImg");
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
    layers.chickHouseLayer = map.createLayer("chick_house", chickHouseTileset);
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
    layers.wallsTileset = map.createLayer("walls", woodenHouseTileset);

    return layers;
  }
};
