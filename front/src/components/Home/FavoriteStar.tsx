import React from "react";
import StarBorderRoundedIcon from "@mui/icons-material/StarBorderRounded";
import StarRoundedIcon from "@mui/icons-material/StarRounded";
import { useIsFavorite } from "../../hooks/FavoriteServer";
import useFavoriteStore from "../../stores/store";

const FavoriteStar = ({ id }: { id: string }) => {
  const isFavorite = useIsFavorite(id);
  const setFavorite = useFavoriteStore((state) => state.setFavorites);

  return (
    <>
      {!isFavorite ? (
        <StarBorderRoundedIcon onClick={() => setFavorite(id, !isFavorite)} />
      ) : (
        <StarRoundedIcon onClick={() => setFavorite(id, !isFavorite)} />
      )}
    </>
  );
};

export default FavoriteStar;
