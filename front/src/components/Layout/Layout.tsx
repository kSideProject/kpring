import Header from "./Header";
import LeftSideBar from "./LeftSideBar";

const Layout = (props: { children: React.ReactNode }) => {
  return (
    <div>
      <Header />
      <LeftSideBar />

      <main>{props.children}</main>
    </div>
  );
};

export default Layout;
