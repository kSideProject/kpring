import { Scene } from "phaser";
import { Layers } from "../../../types/server";

export class MainMap extends Scene {
  private mapInstance!: Phaser.Game;
  private character!: Phaser.Physics.Arcade.Sprite;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys;

  constructor() {
    super("MainMap");
  }

  init(data: { mapInstance: Phaser.Game }) {
    this.mapInstance = data.mapInstance;
  }

  create() {
    // 3. preload에서 tilemapTiledJSON에서 지정한 string key와 매치시켜 map으로 지정
    const map = this.make.tilemap({ key: "firstMap" });

    // 4. Tiled에서 그린 잔디, 집, 나무 등과 같은 타일 요소들을 화면에 뿌려준다.
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

    // 텍스처(캐릭터 이미지) 로드가 완료되었는지 확인
    this.load.on("complete", () => {
      if (this.textures.exists("basic_character")) {
        const frames = this.textures.get("basic_character").getFrameNames();
        console.log(`로드된 프레임: ${frames}`);

        // 텍스처 로드가 완료되면 캐릭터 생성
        this.character = this.physics.add.sprite(
          300,
          300,
          "basic_character", // preload파일에서 atlas의 key값과 동일한 key값
          "move-down-3.png" // 움직이지 않는 상태의 기본 캐릭터
        );

        // Move-Down
        this.anims.create({
          key: "basic_character_move_down",
          frames: this.anims.generateFrameNames("basic_character", {
            start: 1,
            end: 4,
            prefix: "move-down-",
            suffix: ".png",
          }),
          frameRate: 15,
          repeat: -1,
        });

        // Move-Up
        this.anims.create({
          key: "basic_character_move_up",
          frames: this.anims.generateFrameNames("basic_character", {
            start: 1,
            end: 4,
            prefix: "move-up-",
            suffix: ".png",
          }),
          frameRate: 15,
          repeat: -1,
        });

        // Move-Left
        this.anims.create({
          key: "basic_character_move_left",
          frames: this.anims.generateFrameNames("basic_character", {
            start: 1,
            end: 4,
            prefix: "move-left-",
            suffix: ".png",
          }),
          frameRate: 15,
          repeat: -1,
        });

        // Move-Right
        this.anims.create({
          key: "basic_character_move_right",
          frames: this.anims.generateFrameNames("basic_character", {
            start: 1,
            end: 4,
            prefix: "move-right-",
            suffix: ".png",
          }),
          frameRate: 15,
          repeat: -1,
        });

        this.keyboards = this.input.keyboard?.createCursorKeys()!;
        this.cameras.main.startFollow(this.character);
      } else {
        console.log("noooooo");
      }
    });
    this.load.start();

    // 초기 랜더링 맵 크기 지정
    // const mapWidth = map.widthInPixels;
    // const mapHeight = map.heightInPixels;

    // this.cameras.main.setBounds(0, 0, mapWidth, mapHeight);
    // this.physics.world.setBounds(0, 0, mapWidth, mapHeight);

    return layers;
  }

  update() {
    if (!this.character) {
      // 만약 캐리터가 로드되지 않았다면 아무것도 반환하지 않는다
      return;
    }

    this.character.setVelocity(0);

    if (this.keyboards.down.isDown) {
      this.character.setVelocityY(100);
      this.character.anims.play("basic_character_move_down", true);
    }
    if (this.keyboards.up.isDown) {
      this.character.setVelocityY(-100);
      this.character.anims.play("basic_character_move_up", true);
    }
    if (this.keyboards.left.isDown) {
      this.character.setVelocityX(-100);
      this.character.anims.play("basic_character_move_left", true);
    }
    if (this.keyboards.right.isDown) {
      this.character.setVelocityX(100);
      this.character.anims.play("basic_character_move_right", true);
    } else {
      this.character.anims.stop();
    }
  }
}
