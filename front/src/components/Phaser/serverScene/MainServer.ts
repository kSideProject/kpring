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

    // 초기 랜더링 맵 크기 지정
    const mapWidth = map.widthInPixels;
    const mapHeight = map.heightInPixels;

    this.cameras.main.setBounds(0, 0, mapWidth, mapHeight);
    this.physics.world.setBounds(0, 0, mapWidth, mapHeight);

    if (this.input.mouse) {
      this.input.mouse.enabled = true;
    }

    const followSprite = this.add.sprite(0, 0, "transparent");
    this.cameras.main.startFollow(followSprite);

    this.input.on(
      "pointerdown",
      (pointer: Phaser.Input.Pointer) => {
        this.cameras.main.stopFollow(); // 카메라 따라가기 중지
        this.input.on("pointermove", this.onDrag, this);
      },
      this
    );

    this.input.on(
      "pointerup",
      (pointer: Phaser.Input.Pointer) => {
        this.input.off("pointermove", this.onDrag, this); // 드래그 이벤트 해제
        // 스프라이트를 새로운 위치로 이동시켜 카메라가 따라가도록 설정
        followSprite.setPosition(
          this.input.x + this.cameras.main.scrollX,
          this.input.y + this.cameras.main.scrollY
        );
        this.cameras.main.startFollow(followSprite); // 카메라 따라가기 재시작
      },
      this
    );

    return layers;
  }

  onDrag(this: Phaser.Scene, pointer: Phaser.Input.Pointer) {
    // 마우스 이동에 따라 카메라를 이동시킴
    if (pointer.prevPosition) {
      this.cameras.main.scrollX -= (pointer.x - pointer.prevPosition.x) * 1.5;
      this.cameras.main.scrollY -= (pointer.y - pointer.prevPosition.y) * 1.5;
    }
  }
}
