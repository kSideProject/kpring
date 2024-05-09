import Header from "./Header";
import RightSideBar from "./RightSideBar";

const Layout = (props: { children: React.ReactNode }) => {
  return (
    <div>
      <Header />
      <RightSideBar />
      <main>{props.children}</main>
    </div>
  );
};

export default Layout;
