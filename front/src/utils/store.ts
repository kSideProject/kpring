import { create } from "zustand";

interface FavoriteState {
  favorites: Record<string, boolean>;
  setFavorites: (id: string, isFavorite: boolean) => void;
}

const useStore = create<FavoriteState>()((set) => ({
  favorites: {},
  setFavorites: (id, isFavorite) =>
    set((state) => ({
      favorites: {
        ...state.favorites,
        [id]: isFavorite,
      },
    })),
}));

export default useStore;
