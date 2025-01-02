import { ServerMember } from "@/types/server";
import React from "react";

interface MemberListItemProps {
  member: ServerMember;
}

const VideoCallBoxListItem: React.FC<MemberListItemProps> = ({ member }) => {
  return (
    <div className="h-full w-full flex flex-col items-center">
      <div className="bg-gray-800 w-80 h-80 rounded-xl flex flex-col items-center justify-center relative">
        {/* <video ref={videoRef} autoPlay className='w-full h-full rounded-2xl'></video> */}
        <div>
          <div className="text-white absolute bottom-2 right-4">
            {member.name}
          </div>
        </div>
      </div>
    </div>
  );
};

export default VideoCallBoxListItem;
