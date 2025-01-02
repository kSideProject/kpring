// TODO : 비디오 툴바
import { BsArrowDown, BsShare, BsWindowDesktop } from "react-icons/bs";
import { HiCamera } from "react-icons/hi";
import { IoMic, IoSearchCircleOutline } from "react-icons/io5";
import { RiScreenshotFill, RiStopCircleLine } from "react-icons/ri";

const VideoCallToolBar = () => {
  return (
    <div className="bg-white w-52 h-12 rounded-3xl flex items-center justify-around px-2">
      <div className="pointer">
        <IoMic />
      </div>
      <div className="pointer">
        <HiCamera />
      </div>
      <div>
        <BsWindowDesktop />
      </div>
    </div>
  );
};

export default VideoCallToolBar;
