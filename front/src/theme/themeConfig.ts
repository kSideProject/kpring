import { createTheme } from "@mui/material/styles";
import { globalColors } from "../style/globalStyles";

const theme = createTheme({
  palette: {
    primary: {
      light: globalColors.lightPink,
      main: globalColors.secondary,
      dark: globalColors.pink,
      contrastText: globalColors.white,
    },
    secondary: {
      light: globalColors.primary,
      main: globalColors.primary,
      dark: globalColors.pink,
      contrastText: globalColors.white,
    },
  },

  components: {
    MuiButton: {
      styleOverrides: {
        contained: {
          backgroundColor: globalColors.primary,
          color: globalColors.white,
          "&:hover": {
            backgroundColor: globalColors.secondary,
          },
        },
        outlined: {
          borderColor: globalColors.primary,
          color: globalColors.primary,
          "&:hover": {
            borderColor: globalColors.secondary,
            color: globalColors.secondary,
          },
        },
      },
    },
  },
});

export default theme;
