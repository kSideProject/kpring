import axiosInstance from "@/api/axios/axiosInstance";
import { SERVER_API } from "@/api/axios/requests";
import { GetServerType } from "@/types/server";
import { create } from "zustand";

type BookmarkedStoreState = {
  bookmarked: Record<string, boolean>;
  setBookmarked: (id: string, isBookmarked: boolean) => void;
  fetchBookmarked: () => Promise<void>;
};

const useBookmarkedStore = create<BookmarkedStoreState>()((set) => ({
  bookmarked: {},
  setBookmarked: (id, isBookmarked) => {
    set((state) => ({
      bookmarked: {
        ...state.bookmarked,
        [id]: isBookmarked,
      },
    }));
  },
  fetchBookmarked: async () => {
    try {
      const response = await axiosInstance({
        ...SERVER_API.GET_REQUEST.fetchServers,
      });
      const data = await response.data.data;

      const bookmarked = data.reduce(
        (acc: Record<string, boolean>, server: GetServerType) => {
          acc[server.id] = server.bookmarked;
          return acc;
        },
        {}
      );

      set({ bookmarked });
    } catch (error) {
      console.log(error);
    }
  },
}));

export default useBookmarkedStore;
