import { ThemeProvider } from "@mui/material/styles";
import { Outlet } from "react-router-dom";
import theme from "../../theme/themeConfig";
// import Header from "./Header";
import LeftSideBar from "./LeftSideBar";
import Header from "./components/header/Header";

const Layout: React.FC = () => {
  return (
    <div className="relative">
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
