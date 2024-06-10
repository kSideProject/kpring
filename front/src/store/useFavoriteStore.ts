import { create } from "zustand";

interface StoreState {
  favorites: Record<string, boolean>;
  setFavorites: (id: string, isFavorite: boolean) => void;
}

const useFavoriteStore = create<StoreState>()((set) => ({
  favorites: {},
  setFavorites: (id, isFavorite) =>
    set((state) => ({
      favorites: {
        ...state.favorites,
        [id]: isFavorite,
      },
    })),
}));

export default useFavoriteStore;
