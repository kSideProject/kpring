import React from 'react';
import OpenViduVideoComponent from './OpenViduVideoComponent';
import { StreamManager } from 'openvidu-browser';
import { Box } from '@mui/material';


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
        <Box sx={{height: '100%'}}>
            {streamManager !== undefined ? (
                <Box sx={{width: '100%', height: '100%', backgroundColor: 'black', borderRadius: '10px', position: 'relative'}}>
                    {/* OpenViduVideoComponent : 사용자 비디오 컴포넌트 */}
                    <OpenViduVideoComponent streamManager={streamManager}/>
                    <div><p className='text-white absolute bottom-0 left-0'>{getNicknameTag()}</p></div>
                </Box>
            ) : null}
        </Box>
    );
}

export default UserVideoComponent;