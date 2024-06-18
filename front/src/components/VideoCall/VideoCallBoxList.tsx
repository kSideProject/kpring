import React, { useCallback, useEffect, useMemo, useState } from 'react'
import VideoCallBoxListItem from './VideoCallBoxListItem'
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Member } from '../../types/layout';
import { Box } from '@mui/material';

const VideoCallBoxList = () => {
  const [curVideoCallBoxPage, setCurVideoCallBoxPage] = useState(0);
  const [slicedMemberList, setSlicedMemberList] = useState<Member[]>([]); // 페이징 처리 된 멤버 리스트
  const serverMemberList = [  {
    id: 13,
    userName: "test",
    profilePath: "https://helpx.adobe.com/content/dam/help/en/photoshop/using/quick-actions/remove-background-before-qa1.png"
  },];

  // 마지막 페이지 수
  const lastPage = useMemo(()=>{
    const memberCnt = serverMemberList.length;
    let lastPage = 0;
    if(memberCnt%4 === 0){
      lastPage = Math.floor(memberCnt/4) - 1
    }else{
      lastPage = Math.floor(memberCnt/4)
    }
    return lastPage
  },[])

  // TODO : 화면 공유 박스 이전 페이지 이동 핸들링 함수
  const handleBoxPagePrev = useCallback(()=>{
    let curPage = curVideoCallBoxPage;
    if(curPage!==0){
      setCurVideoCallBoxPage(curPage - 1)
    }
  },[curVideoCallBoxPage])

  // // TODO : 화면 공유 박스 다음 페이지 이동 핸들링 함수
  const handleBoxPageNext = useCallback(()=>{
    if(curVideoCallBoxPage!==lastPage){
      let curPage = curVideoCallBoxPage;
      setCurVideoCallBoxPage(curPage + 1)
    }
  },[curVideoCallBoxPage,lastPage])


  // TODO : 화면공유 멤버 리스트 슬라이싱 함수 
  const sliceMemberList = useCallback(()=>{
    const newMemberList = serverMemberList.slice(curVideoCallBoxPage*4, (curVideoCallBoxPage*4)+4);
    setSlicedMemberList(newMemberList)
  },[curVideoCallBoxPage])

  useEffect(() => {
    sliceMemberList();
  }, [sliceMemberList]);
  
  return (
    <Box sx={{width: '880px', height:'160px', display:'flex', alignItems:'center'}}>
      <ArrowBackIosIcon 
      sx={{
        color: `${curVideoCallBoxPage === 0 ? 'gray': 'black'}`,
        cursor: 'pointer'
      }}
      onClick={handleBoxPagePrev} />
      <Box   sx={{
        display: 'grid',
        gridTemplateColumns: 'repeat(4, 1fr)',
        height: '100%',
        width: '100%'
      }}>
        {
            slicedMemberList.map((member,index)=>(
                <VideoCallBoxListItem key={index} member={member}/>
            ))
        }
      </Box>
      <ArrowForwardIosIcon 
      sx={{
        color: `${curVideoCallBoxPage === 0 ? 'gray': 'black'}`,
        cursor: 'pointer'
      }}
      onClick={handleBoxPageNext}
      />
    </Box>
  )
}

export default VideoCallBoxList