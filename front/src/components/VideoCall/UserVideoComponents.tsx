import React from "react";
import OpenViduVideoComponent from "./OpenViduVideoComponent";
import { StreamManager } from "openvidu-browser";

interface UserVideoComponentProps {
  streamManager: StreamManager;
}
// 모든 사용자 비디오 표시, 클릭이벤트 처리
const UserVideoComponent: React.FC<UserVideoComponentProps> = ({
  streamManager,
}) => {
  const getNicknameTag = (): string => {
    // Gets the nickName of the user
    return JSON.parse(streamManager!.stream.connection.data).clientData;
  };

  return (
    <div className="h-full">
      {streamManager !== undefined ? (
        <div className="w-full h-full bg-white rounded-md relative">
          {/* OpenViduVideoComponent : 사용자 비디오 컴포넌트 */}
          <OpenViduVideoComponent streamManager={streamManager} />
          <div>
            <p className="text-white absolute bottom-0 left-0">
              {getNicknameTag()}
            </p>
          </div>
        </div>
      ) : null}
    </div>
  );
};

export default UserVideoComponent;
