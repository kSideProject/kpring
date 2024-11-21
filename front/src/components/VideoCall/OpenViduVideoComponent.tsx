import { StreamManager } from 'openvidu-browser';
import React, { useRef, useEffect } from 'react';


interface OpenViduVideoComponentProps {
    streamManager: StreamManager | undefined;
}

// 최종적으로 미디어 스트림을 표시하는 최종 HTMl 래핑.
const OpenViduVideoComponent: React.FC<OpenViduVideoComponentProps> = ({ streamManager }) => {
    const videoRef = useRef<HTMLVideoElement>(null); // videoRef의 타입을 HTMLVideoElement로 지정

    useEffect(() => {
        if (streamManager && videoRef.current) {
            streamManager.addVideoElement(videoRef.current);
        }
    }, [streamManager]);

    return <video className='w-40 h-32 rounded-lg' autoPlay ref={videoRef} />;
}

export default OpenViduVideoComponent;