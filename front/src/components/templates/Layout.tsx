import { Outlet } from "react-router-dom";
import LeftSideBar from "../organisms/LeftSideBar";
import Header from "../organisms/Header";

const Layout: React.FC = () => {
  return (
    <div className="relative flex flex-col h-screen">
      <div className="sticky top-0 z-10">
        <Header />
      </div>

      <div className="flex flex-grow overflow-hidden">
        <div className="fixed top-14 z-10 h-screen w-20 bg-quaternary transition-all duration-300 group hover:w-60 overflow-y-auto">
          <LeftSideBar />
        </div>

        <div className="flex-grow overflow-y-auto">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Layout;
