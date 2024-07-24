export const getRandomCostume = (
  scene: Phaser.Scene,
  atlasKey: string
): { frame: string; key: string; color: string } | null => {
  const atlasTexture = scene.textures.get(atlasKey);
  const frames = atlasTexture
    .getFrameNames()
    .filter((key) => key.endsWith("front-1.png"));

  const colorGroups: { [key: string]: string[] } = frames.reduce((acc, key) => {
    const match = key.match(/^([^-]+)-([^-]+)-/);

    if (match) {
      const color = match[2];
      if (!acc[color]) {
        acc[color] = [];
      }
      acc[color].push(key);
    }

    return acc;
  }, {} as { [key: string]: string[] });

  const colorKeys = Object.keys(colorGroups);
  if (colorKeys.length === 0) return null;

  const randomColor = colorKeys[Math.floor(Math.random() * colorKeys.length)];
  const costumes = colorGroups[randomColor];
  const randomIndex = Math.floor(Math.random() * costumes.length);
  const seletedFrame = costumes[randomIndex];

  const match = seletedFrame.match(/^([^-]+)-([^-]+)-/);
  if (!match) return null;

  return { frame: seletedFrame, key: match[1], color: match[2] };
};
