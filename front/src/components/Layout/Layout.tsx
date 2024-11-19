import { ThemeProvider } from "@mui/material/styles";
import { Outlet } from "react-router-dom";
import theme from "../../theme/themeConfig";
import Header from "./components/header/Header";
import { LeftSidebar } from "./components/leftSidebar/LeftSidebar";
// import Header from "./Header";

const Layout: React.FC = () => {
  return (
    <div>
      <Header />
      <LeftSidebar />
      <main>
        <ThemeProvider theme={theme}>
          <Outlet />
        </ThemeProvider>
      </main>
    </div>
  );
};

export default Layout;
