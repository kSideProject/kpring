import React, { useCallback, useEffect, useMemo, useState } from 'react'
import VideoCallBoxListItem from './VideoCallBoxListItem'
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Box } from '@mui/material';
import axios from 'axios';
import { useParams } from 'react-router';
import { ServerMember } from '../../types/server';

const VideoCallBoxList = () => {
  const [curVideoCallBoxPage, setCurVideoCallBoxPage] = useState(0);
  const [slicedMemberList, setSlicedMemberList] = useState<ServerMember[]>([]); // 페이징 처리 된 멤버 리스트
  const [serverMemberList, setServerMemberList] = useState([]);
  // const serverId = useParams();

  // FIXME : 해당 API axios 인터셉터 개발되면 수정 필요
  const accessToken = localStorage.getItem('dicoTown_AccessToken');
  const getServerMember = useCallback(()=>{
    axios.get(`http://kpring.duckdns.org/server/api/v1/server/66795ea1981fc767b76ca9d0`,{
      headers: {'Authorization': `${accessToken}`}
    })
    .then(response=>{
      setServerMemberList(response.data.data.users);
    })
    .catch(err=>console.log(err))
  },[accessToken])


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
  },[serverMemberList])

  // 화면 공유 박스 이전 페이지 이동 핸들링 함수
  const handleBoxPagePrev = useCallback(()=>{
    let curPage = curVideoCallBoxPage;
    if(curPage!==0){
      setCurVideoCallBoxPage(curPage - 1)
    }
  },[curVideoCallBoxPage])

  // 화면 공유 박스 다음 페이지 이동 핸들링 함수
  const handleBoxPageNext = useCallback(()=>{
    if(curVideoCallBoxPage!==lastPage){
      let curPage = curVideoCallBoxPage;
      setCurVideoCallBoxPage(curPage + 1)
    }
  },[curVideoCallBoxPage,lastPage])


  // 화면공유 멤버 리스트 슬라이싱 함수 
  const sliceMemberList = useCallback(()=>{
    const newMemberList = serverMemberList.slice(curVideoCallBoxPage*4, (curVideoCallBoxPage*4)+4);
    setSlicedMemberList(newMemberList)
  },[curVideoCallBoxPage, serverMemberList])

  // 무한루프 방지를 위해 useEffect 분리
  useEffect(()=>{
    getServerMember();
  },[getServerMember])

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