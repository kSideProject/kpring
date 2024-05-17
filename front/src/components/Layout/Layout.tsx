import { ThemeProvider, createTheme } from "@mui/material/styles";
import { Outlet } from "react-router-dom";
import Header from "./Header";
import LeftSideBar from "./LeftSideBar";
const Layout: React.FC = () => {
  const theme = createTheme({
    palette: {
      primary: {
        light: "#FDE2F3",
        main: "#917FB3",
        dark: "#FDE2F3",
        contrastText: "#000",
      },
      secondary: {
        light: "#2A2F4F",
        main: "#2A2F4F",
        dark: "#FDE2F3",
        contrastText: "#fff",
      },
    },
  });
  return (
    <div>
      <Header />
      <LeftSideBar />

      <main>
        <ThemeProvider theme={theme}>
          <Outlet />
        </ThemeProvider>
      </main>
    </div>
  );
};

export default Layout;
