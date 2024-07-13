export const getRandomCostume = (
  scene: Phaser.Scene,
  atlasKey: string
): string | null => {
  const atlasTexture = scene.textures.get(atlasKey);
  const frames = atlasTexture
    .getFrameNames()
    .filter((key) => key.endsWith("front-1.png"));

  const colorGroups: { [key: string]: string[] } = frames.reduce((acc, key) => {
    const color = key.split("-")[1];
    if (!acc[color]) {
      acc[color] = [];
    }
    acc[color].push(key);
    return acc;
  }, {} as { [key: string]: string[] });

  const colorKeys = Object.keys(colorGroups);
  if (colorKeys.length === 0) return null;

  const randomColor = colorKeys[Math.floor(Math.random() * colorKeys.length)];
  const costumes = colorGroups[randomColor];
  const randomIndex = Math.floor(Math.random() * costumes.length);

  return costumes[randomIndex];
};
