export interface ThemeType {
  id: string;
  name: string;
}

export interface CategoriesType {
  id: string;
  name: string;
}

export interface ServerType {
  serverName: string;
  userId: string;
  theme: ThemeType | null;
  categories: CategoriesType[] | null;
}

export interface ServerResponseType {
  id: string;
  name: string;
  bookmarked: boolean;
}

export interface FetchedServerType {
  data: ServerResponseType[];
}

export interface ServerCardProps {
  server: ServerType;
}

export interface ServerCardListProps {
  servers: ServerType[];
}

// 서버 멤버 조회
export interface ServerMember {
  id : number;
  name : string;
  profileImage : string;
}
