export type ServerType = {
  serverName: string;
  userId: string | null;
  hostName: string | null;
  theme: ThemeType | null;
  categories: CategoriesType[] | null;
};

export type ThemeType = {
  id: string;
  name: string;
};

export type CategoriesType = {
  id: string;
  name: string;
};

export type GetServerType = {
  id: string;
  name: string;
  bookmarked: boolean;
  hostName: string;
  categories: CategoriesType[] | null;
  theme: ThemeType | null;
};

export type CategoriesResponseType = {
  data: CategoriesType[];
};

export type ServerResponseType = {
  data: GetServerType[];
};

export interface ServerCardProps {
  server: ServerType;
}

export interface ServerCardListProps {
  servers: ServerType[];
}

// 서버 멤버 조회
export interface ServerMember {
  id: number;
  name: string;
  profileImage: string;
}

export interface SelectedType extends ServerType {
  users: ServerMember[];
}

// 서버 생성
export type CreateServerRequest = {
  serverName: string;
  userId: string;
  hostName: string;
  theme: string;
  categories: string[];
};

export type CreateServerResponse = {
  data: {
    serverId: string;
    serverName: string;
    hostName: string;
    theme: ThemeType | null;
    categories: CategoriesType[] | null;
  };
};
