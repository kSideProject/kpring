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
      {activeSideBar === "friends" ? (
        <span className="text-h6 font-bold">Friends</span>
      ) : (
        <span className="text-h6 font-bold">Massages</span>
      )}
      <IoCloseCircle
        onClick={onClose}
        className="text-h4 text-black cursor-pointer transition duration-300 hover:text-red-500"
      />
    </div>
  );
};

export default SideBarHaeder;
