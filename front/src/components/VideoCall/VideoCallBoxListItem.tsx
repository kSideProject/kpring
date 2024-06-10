import React, { useEffect, useRef } from 'react'
import { Member } from '../../types/layout'
import { Avatar } from '@mui/material';

interface MemberListItemProps{
  member: Member;
}

const VideoCallBoxListItem : React.FC<MemberListItemProps>= ({member}) => {
  const videoRef = useRef<HTMLVideoElement>(null);
  const videoShare = async() => {
    try{
      const stream = await navigator.mediaDevices.getDisplayMedia({
        video: true
      })
      if (videoRef.current) {
        videoRef.current.srcObject = stream;
      }
      return stream
    }catch(err){
      console.error("미디어 접근에 에러가 있습니다", err)
      return null
    }
  }
  useEffect(()=>{
    videoShare();
  },[])
  
  return (
    <div className='h-full w-full flex flex-col items-center'>
        <div className='bg-slate-800 w-4/5 h-4/5 rounded-2xl flex-center flex flex-col justify-center items-center shadow-lg relative'>
        <video ref={videoRef} autoPlay className='w-full h-full rounded-2xl'></video>
          <div>
            <div className='text-white absolute bottom-2 right-4'>{member.userName}</div>
          </div>
          
        </div>
        
        
    </div>
  )
}

export default VideoCallBoxListItem