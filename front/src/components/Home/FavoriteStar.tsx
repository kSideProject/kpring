import StarBorderRoundedIcon from "@mui/icons-material/StarBorderRounded";
import StarRoundedIcon from "@mui/icons-material/StarRounded";
import { useIsFavorite } from "../../hooks/FavoriteServer";
import useFavoriteStore from "../../stores/store";

const FavoriteStar = ({ id }: { id: string }) => {
  const isFavorite = useIsFavorite(id);
  const setFavorite = useFavoriteStore((state) => state.setFavorites);

  return (
    <div onClick={() => setFavorite(id, !isFavorite)}>
      {!isFavorite ? <StarBorderRoundedIcon /> : <StarRoundedIcon />}
    </div>
  );
};

export default FavoriteStar;
