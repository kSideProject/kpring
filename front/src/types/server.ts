export interface ServerTypes {
  server: Phaser.Game | null;
  scene: Phaser.Scene | null;
}

// 현재 활성화된 씬을 부모 컴포넌트로 전달하는 콜백 함수
export interface ServerProps {
  currentActiveScene?: (scene_instance: Phaser.Scene) => void;
}

// Tiled Map 레이어 타입
export interface Layers {
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
