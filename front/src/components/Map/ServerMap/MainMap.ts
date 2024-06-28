import { Scene } from "phaser";
import { charanterAnimation, createCharacter } from "./Character";
import { createLayers } from "./Layers";
import { addCollider, debugCollision } from "./Debug";

export class MainMap extends Scene {
  private mapInstance!: Phaser.Game;
  private character!: Phaser.Physics.Arcade.Sprite;
  private keyboards!: Phaser.Types.Input.Keyboard.CursorKeys;
  private speechBalloon!: Phaser.GameObjects.Text;

  constructor() {
    super("MainMap");
  }

  init(data: { mapInstance: Phaser.Game }) {
    this.mapInstance = data.mapInstance;
  }

  create() {
    // preload에서 tilemapTiledJSON에서 지정한 string key와 매치시켜 map으로 지정
    const map = this.make.tilemap({ key: "firstMap" });

    // 타일 레이어 생성
    const layers = createLayers(map, this);

    // 타일 레이어가 생성되었을 경우에 debugCollision 함수 호출
    if (layers) {
      debugCollision(this, layers);
    } else {
      console.log("레이어가 생성되지 않았습니다.");
    }

    // 캐릭터 택스처 파일 로드 완료 여부에 따른 함수 호출
    this.load.on("complete", () => {
      try {
        this.character = createCharacter(this, 300, 300);
        charanterAnimation(this);

        this.keyboards = this.input.keyboard?.createCursorKeys()!; // 방향키로 캐릭터 조정
        this.cameras.main.startFollow(this.character); // 캐릭터의 움직을 따라 카메라 움직임
        this.cameras.main.setZoom(2);

        // 캐릭터가 접근할 수 없는 곳 블로킹
        if (layers) {
          addCollider(this, this.character, layers);
        } else {
          console.log("레이어가 생성되지 않았습니다.");
        }
        // 말풍선
        this.speechBalloon = this.add
          .text(this.character.x, this.character.y - 20, "", {
            fontSize: 10,
            color: "#000",
            backgroundColor: "#fff",
            padding: { x: 10, y: 10 },
            resolution: 2,
          })
          .setOrigin(0.5);

        window.addEventListener("updateBalloonText", (event: Event) => {
          const customEvent = event as CustomEvent<string>;
          this.setBalloonText(customEvent.detail);
        });
      } catch (error) {
        console.error(error);
      }
    });

    this.load.start();

    const mapWidth = map.widthInPixels;
    const mapHeight = map.heightInPixels;
    this.cameras.main.setBounds(0, 0, mapWidth, mapHeight);
    this.physics.world.setBounds(0, 0, mapWidth, mapHeight);

    return layers;
  }

  update() {
    if (!this.character) {
      // 만약 캐리터가 로드되지 않았다면 아무것도 반환하지 않는다.
      return;
    }

    this.character.setVelocity(0);

    this.input.keyboard?.on("keydown", (e: KeyboardEvent) => {
      if (document.activeElement?.tagName === "INPUT") {
        this.input.keyboard?.clearCaptures();
      }
    });

    if (this.keyboards.down.isDown) {
      this.character.setVelocityY(100);
      this.character.anims.play("basic_character_move_down", true);
    } else if (this.keyboards.up.isDown) {
      this.character.setVelocityY(-100);
      this.character.anims.play("basic_character_move_up", true);
    } else if (this.keyboards.left.isDown) {
      this.character.setVelocityX(-100);
      this.character.anims.play("basic_character_move_left", true);
    } else if (this.keyboards.right.isDown) {
      this.character.setVelocityX(100);
      this.character.anims.play("basic_character_move_right", true);
    } else {
      this.character.anims.stop();
    }
    this.speechBalloon.setPosition(this.character.x, this.character.y - 50);
  }

  setBalloonText(text: string) {
    if (this.speechBalloon) {
      this.speechBalloon.setText(text);
    }
  }
}
