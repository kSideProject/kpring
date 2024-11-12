import Phaser from "phaser";

/**
 * Phaser 카메라에 줌 인/줌 아웃 및 드래그 이동 기능을 추가하는 함수
 * @param scene - Phaser.Scene 인스턴스 (카메라 기능이 있는 씬)
 * @param avatar - 카메라가 따라갈 아바타 (드래그 중단 후 다시 따라감)
 */
export function setupCameraControls(
  scene: Phaser.Scene,
  avatar: Phaser.GameObjects.Container
) {
  let isDragging = false;
  let dragStartPoint = new Phaser.Math.Vector2();

  // 마우스 휠을 통한 줌 인/줌 아웃
  scene.input.on(
    "wheel",
    (
      pointer: Phaser.Input.Pointer,
      gameObjects: Phaser.GameObjects.GameObject[],
      deltaX: number,
      deltaY: number,
      deltaZ: number
    ) => {
      const zoomFactor = 0.1;
      if (deltaY > 0) {
        scene.cameras.main.zoom = Math.max(
          scene.cameras.main.zoom - zoomFactor,
          0.5
        );
      } else if (deltaY < 0) {
        scene.cameras.main.zoom = Math.min(
          scene.cameras.main.zoom + zoomFactor,
          3
        );
      }
    }
  );

  // 마우스 클릭과 드래그로 화면 이동
  scene.input.on("pointerdown", (pointer: Phaser.Input.Pointer) => {
    isDragging = true;
    dragStartPoint.set(pointer.x, pointer.y);
    scene.cameras.main.stopFollow();
  });

  scene.input.on("pointermove", (pointer: Phaser.Input.Pointer) => {
    if (isDragging) {
      const dragEndPoint = new Phaser.Math.Vector2(pointer.x, pointer.y);
      const dragDistance = dragStartPoint.clone().subtract(dragEndPoint);

      scene.cameras.main.scrollX += dragDistance.x / scene.cameras.main.zoom;
      scene.cameras.main.scrollY += dragDistance.y / scene.cameras.main.zoom;

      dragStartPoint.set(pointer.x, pointer.y);
    }
  });

  scene.input.on("pointerup", () => {
    isDragging = false;
    scene.cameras.main.startFollow(avatar);
  });
}
