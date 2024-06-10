import { Scene } from "phaser";
import { Layers } from "../../../types/map";

export const createLayers = (map: Phaser.Tilemaps.Tilemap, scene: Scene) => {
  // Tiled에서 그린 잔디, 집, 나무 등과 같은 타일 요소들을 화면에 뿌려준다.
  // preload에서 지정한 이미지 파일 keymap.addTilesetImage(Tiled에서 지정한 tilessets의 이름, preload에서 지정한 이미지 파일 key);
  const hillsTileset = map.addTilesetImage("hills", "hillImg");
  const dirtTileset = map.addTilesetImage("dirt", "dirtImg");
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
  const hillsCollidesTileset = map.addTilesetImage(
    "hills_collider",
    "hillCollidesImg"
  );

  let layers: Layers = {};

  if (
    grassTileset &&
    waterTileset &&
    chickHouseTileset &&
    basicGrassTileset &&
    bridgeTileset &&
    dirtTileset &&
    basicPlantsTileset &&
    hillsTileset &&
    woodenHouseTileset &&
    cowTileset &&
    fenceTileset &&
    eggsTileset &&
    chickenTileset &&
    furnitureTilset &&
    hillsCollidesTileset
  ) {
    // 순서: 제일 위에 있는 layer가 가장 밑에 깔림
    layers.mapLayer = map.createLayer("map", waterTileset);
    layers.groundLayer = map.createLayer("grass", grassTileset);
    layers.fenceLayer = map.createLayer("fence", fenceTileset);
    layers.chickHouseLayer = map.createLayer("chick_house", chickHouseTileset);
    layers.bridgeLayer = map.createLayer("bridge", bridgeTileset);
    layers.dirtLayer = map.createLayer("dirt", dirtTileset);
    layers.basicPlantsLayer = map.createLayer(
      "basic_plants",
      basicPlantsTileset
    );
    layers.hillsLayer = map.createLayer("hills", hillsTileset);
    layers.hillsCollidesLayer = map.createLayer(
      "hills_collides",
      hillsCollidesTileset
    );
    layers.woodenHouseLayer = map.createLayer("house", woodenHouseTileset);
    layers.basicGrassLayer = map.createLayer("basic_grass", basicGrassTileset);
    layers.cowLayer = map.createLayer("cow", cowTileset);
    layers.eggsLayer = map.createLayer("eggs", eggsTileset);
    layers.chickenLayer = map.createLayer("chicken", chickenTileset);
    layers.furnitureLayer = map.createLayer("furniture", furnitureTilset);
    layers.wallsLayer = map.createLayer("walls", woodenHouseTileset);

    return layers;
  }
};
