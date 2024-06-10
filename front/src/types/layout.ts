// Header

import { AppBarProps as MuiAppBarProps } from "@mui/material/AppBar";
export interface AppBarProps extends MuiAppBarProps {
  open?: boolean;
}

// 오른쪽 사이드바
export interface SideBarProps {
  close: () => void;
}

// 서버 인포 사이드바
export interface ServerInforProps {
  close: () => void;
  open: boolean;
  serverID: string;
}

// FIXME :멤버 조회 타입 임의로 지정(이후 API 명세서 수정에 따라 변경 필요)
export interface Member {
  id: number;
  profilePath: string;
  userName: string;
}
