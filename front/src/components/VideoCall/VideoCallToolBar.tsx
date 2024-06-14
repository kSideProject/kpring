import React from 'react'
import MicIcon from '@mui/icons-material/Mic';
import PhotoCameraFrontIcon from '@mui/icons-material/PhotoCameraFront';
import MonitorIcon from '@mui/icons-material/Monitor';
import ArrowDropUpIcon from '@mui/icons-material/ArrowDropUp';
import { Box } from '@mui/material';

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
    <Box sx={{
        backgroundColor: 'hsla(0, 0%, 100%, .9)',
        width: '200px',
        height: '48px',
        borderRadius: '24px',
        display: 'flex',
        alignItems: 'center',
        justifyContent: 'space-around',
        paddingX: '8px'
    }}>
        <Box sx={{cursor: 'pointer'}}>
            <MicIcon sx={iconStyle}/>
            <ArrowDropUpIcon 
            sx={{
                borderRadius: '3px',
                '&:hover': {
                    backgroundColor: '#C7C8CC',
                }
            }}/>
        </Box>
        <Box sx={{cursor: 'pointer'}}>
            <PhotoCameraFrontIcon  sx={iconStyle}/>
            <ArrowDropUpIcon 
            sx={{
                borderRadius: '3px',
                '&:hover': {
                    backgroundColor: '#C7C8CC',
                }
            }}
            />
        </Box>
        <Box sx={{cursor: 'pointer'}}>
            <MonitorIcon sx={iconStyle}/>
        </Box>

    </Box>
  )
}

export default VideoCallToolBar