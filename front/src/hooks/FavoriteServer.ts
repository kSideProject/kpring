import useFavoriteStore from "../stores/store";

export const useIsFavorite = (id: string) => {
  return useFavoriteStore((state) => state.favorites[id] ?? false);
};

export const useFilterFavorites = () => {
  return useFavoriteStore((state) =>
    Object.keys(state.favorites).filter((id) => state.favorites[id])
  );
};
