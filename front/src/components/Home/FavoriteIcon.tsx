import useStore from "../../utils/store";
import StarBorderIcon from "@mui/icons-material/StarBorder";
import StarIcon from "@mui/icons-material/Star";
import { useIsFavorite } from "../../hooks/Favorites";

const FavoriteIcon = ({ id }: { id: string }) => {
  const isFavorite = useIsFavorite(id);
  const setFavorites = useStore((state) => state.setFavorites);

  return (
    <>
      {!isFavorite ? (
        <StarBorderIcon onClick={() => setFavorites(id, !isFavorite)} />
      ) : (
        <StarIcon onClick={() => setFavorites(id, !isFavorite)} />
      )}
    </>
  );
};

export default FavoriteIcon;
