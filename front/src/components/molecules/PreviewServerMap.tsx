import useEmblaCarousel from "embla-carousel-react";
import Autoplay from "embla-carousel-autoplay";
import { useThemeStore } from "@/store/useThemeStore";
import { useNavigate } from "react-router";
import { ThemeType } from "@/types/server";

const PreviewServerMap = () => {
  const [emblaRef] = useEmblaCarousel({ loop: true }, [Autoplay()]);
  const setTheme = useThemeStore((state) => state.setTheme);
  const navigate = useNavigate();

  const onClickTheme = (selectedtheme: ThemeType, themeTitle: string) => {
    setTheme(selectedtheme);
    navigate(`/${themeTitle}`);
  };

  return (
    <div className="embla ">
      <div className="overflow-hidden" ref={emblaRef}>
        <div className="embla__container rounded-xl">
          <div
            className="embla__slide bg-camping min-h-72 cursor-pointer bg-no-repeat bg-cover bg-center"
            onClick={() =>
              onClickTheme({ id: "SERVER_THEME_001", name: "숲" }, "camping")
            }></div>
          <div
            className="embla__slide bg-beach min-h-72 cursor-pointer bg-no-repeat bg-cover bg-center"
            onClick={() =>
              onClickTheme({ id: "SERVER_THEME_002", name: "오피스" }, "beach")
            }></div>
        </div>
      </div>
    </div>
  );
};

export default PreviewServerMap;
