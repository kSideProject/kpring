import { Outlet } from "react-router-dom";
import LeftSideBar from "../organisms/LeftSideBar";
import Header from "../organisms/Header";

const Layout: React.FC = () => {
  return (
    <div>
      <Header />
      <Outlet />
    </div>
    //   <div className="flex flex-1">
    //     <LeftSideBar />
    //     <div className="flex-1 flex justify-center items-center">
    //     </div>
    //   </div>
  );
};

export default Layout;
