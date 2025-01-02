import { IoCloseCircle } from "react-icons/io5";

type SideBarHeaderProps = {
  activeSideBar: "friends" | "messages" | null;
  onClose: () => void;
};

const SideBarHaeder: React.FC<SideBarHeaderProps> = ({
  activeSideBar,
  onClose,
}) => {
  return (
    <div className="flex justify-between items-center mb-5 max-h-12">
      {activeSideBar === "friends" ? <p>Friends</p> : <p>Massages</p>}
      <IoCloseCircle
        onClick={onClose}
        className="text-3xl text-white hover:text-red-500"
      />
    </div>
  );
};

export default SideBarHaeder;
