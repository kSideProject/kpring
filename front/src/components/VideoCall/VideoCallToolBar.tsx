import React from 'react'
import MicIcon from '@mui/icons-material/Mic';
import PhotoCameraFrontIcon from '@mui/icons-material/PhotoCameraFront';
import MonitorIcon from '@mui/icons-material/Monitor';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';

// TODO : 비디오 툴바
const VideoCallToolBar = () => {
    const iconStyle = {
        color: 'green',
        padding: '2px',
        boxSizing: 'content-box',
        borderRadius: '3px',
        '&:hover': {
          backgroundColor: '#C7C8CC',
        },
      };
  return (
    <div className="bg-gray w-[200px] h-[48px] rounded-3xl flex justify-center items-center justify-around px-2">
        <div className='cursor-pointer'>
            <MicIcon sx={iconStyle}/>
            <ArrowDropUpIcon 
            sx={{
                borderRadius: '3px',
                '&:hover': {
                    backgroundColor: '#C7C8CC',
                }
            }}/>
        </div>
        <div className='cursor-pointer'>
            <PhotoCameraFrontIcon  sx={iconStyle}/>
            <ArrowDropUpIcon 
            sx={{
                borderRadius: '3px',
                '&:hover': {
                    backgroundColor: '#C7C8CC',
                }
            }}
            />
        </div>
        <div className='cursor-pointer'>
            <MonitorIcon sx={iconStyle}/>
        </div>

    </div>
  )
}

export default VideoCallToolBar