import React from 'react'
import { Member } from '../../types/layout'
import { Avatar } from '@mui/material';

interface MemberListItemProps{
  member: Member;
}

const VideoCallBoxListItem : React.FC<MemberListItemProps>= ({member}) => {
  return (
    <div className='h-full w-full flex flex-col items-center'>
        <div className='bg-slate-800 w-4/5 h-4/5 rounded-2xl flex-center flex flex-col justify-center items-center shadow-lg relative'>
          <Avatar src={member.profilePath}></Avatar>
          <div>
            <div className='text-white absolute bottom-2 right-4'>{member.userName}</div>
          </div>
          
        </div>
        
        
    </div>
  )
}

export default VideoCallBoxListItem