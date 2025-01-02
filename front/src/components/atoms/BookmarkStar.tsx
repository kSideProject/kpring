import useBookmarkedStore from "@/store/useBookmarkedStore";
import React from "react";
import { IoStar, IoStarOutline } from "react-icons/io5";

type BookmarkStarProps = {
  id: string;
};

const FavoriteStar: React.FC<BookmarkStarProps> = ({ id }) => {
  const isBookmark = useBookmarkedStore(
    (state) => state.bookmarked[id] ?? false
  );
  const setBookmarked = useBookmarkedStore((state) => state.setBookmarked);

  const handleBookmarkClick = () => {
    setBookmarked(id, !isBookmark);
  };
  return (
    <div onClick={handleBookmarkClick}>
      {!isBookmark ? <IoStarOutline /> : <IoStar />}
    </div>
  );
};

export default FavoriteStar;
