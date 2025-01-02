import { Outlet } from "react-router-dom";
import LeftSideBar from "../organisms/LeftSideBar";
import Header from "../organisms/Header";

const Layout: React.FC = () => {
  return (
    <div className="flex flex-col h-screen">
      <Header />
      <div className="flex flex-1">
        <LeftSideBar />
        <div className="flex-1 flex justify-center items-center bg-gray-100">
          <Outlet />
        </div>
      </div>
    </div>
  );
};

export default Layout;
