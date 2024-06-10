import React, { useCallback, useEffect, useMemo, useState } from 'react'
import VideoCallBoxListItem from './VideoCallBoxListItem'
import { messageMemberList } from "../../utils/fakeData";
import ArrowBackIosIcon from '@mui/icons-material/ArrowBackIos';
import ArrowForwardIosIcon from '@mui/icons-material/ArrowForwardIos';
import { Member } from '../../types/layout';

const VideoCallBoxList = () => {
  const [curVideoCallBoxPage, setCurVideoCallBoxPage] = useState(0);
  const [slicedMemberList, setSlicedMemberList] = useState<Member[]>([]); // 페이징 처리 된 멤버 리스트
  
  // 마지막 페이지 수
  const lastPage = useMemo(()=>{
    const memberCnt = messageMemberList.length;
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
    const newMemberList = messageMemberList.slice(curVideoCallBoxPage*4, (curVideoCallBoxPage*4)+4);
    setSlicedMemberList(newMemberList)
  },[curVideoCallBoxPage])

  useEffect(() => {
    sliceMemberList();
  }, [sliceMemberList]);
  
  return (
    <div className='w-[55rem] h-[10rem] flex items-center'>
      <ArrowBackIosIcon 
      className={`${curVideoCallBoxPage === 0 ? 'text-gray-400' : 'text-black cursor-pointer'}`}
      onClick={handleBoxPagePrev} />
      <div className='grid grid-cols-4 h-full w-full'>
        {
            slicedMemberList.map(member=>(
                <VideoCallBoxListItem member={member}/>
            ))
        }
      </div>
      <ArrowForwardIosIcon 
      className={`${curVideoCallBoxPage===lastPage? 'text-gray-400' : 'text-black cursor-pointer'}`}
      onClick={handleBoxPageNext}
      />
    </div>
  )
}

export default VideoCallBoxList