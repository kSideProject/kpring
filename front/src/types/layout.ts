// 오른쪽 사이드바
export interface RightSideBarProps {
  close: () => void;
}

// 멤버 프로필 모달 스타일
export const memberProfileModalStyle = {
  position: "absolute" as "absolute",
  top: "50%",
  left: "50%",
  transform: "translate(-50%, -50%)",
  width: 400,
  bgcolor: "backround.paper",
  border: "2px solid #000",
  boxShadow: 24,
  p: 4,
};

// 멤버 프로필 모달
export interface MemberProfileModalProps {
  openModal: boolean;
  closeModal: () => void;
}
