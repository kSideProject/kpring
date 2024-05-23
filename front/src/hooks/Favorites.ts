import useStore from "../utils/store";

export const useIsFavorite = (id: string) => {
  return useStore((state) => state.favorites[id] ?? false);
};

export const useFilterFavorites = () => {
  return useStore((state) =>
    Object.keys(state.favorites).filter((id) => state.favorites[id])
  );
};
