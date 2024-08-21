import React from 'react';
import OpenViduVideoComponent from './OvVideo';
import './UserVideo.css';
import { StreamManager } from 'openvidu-browser';


interface UserVideoComponentProps {
    streamManager: StreamManager
}
// 모든 사용자 비디오 표시, 클릭이벤트 처리
const UserVideoComponent: React.FC<UserVideoComponentProps> = ({ streamManager }) => {
    const getNicknameTag = (): string => {
        // Gets the nickName of the user
        return JSON.parse(streamManager!.stream.connection.data).clientData;
    }

    return (
        <div>
            {streamManager !== undefined ? (
                <div className="streamcomponent">
                    {/* OpenViduVideoComponent : 사용자 비디오 */}
                    <OpenViduVideoComponent streamManager={streamManager} />
                    <div><p>{getNicknameTag()}</p></div>
                </div>
            ) : null}
        </div>
    );
}

export default UserVideoComponent;