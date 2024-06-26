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

export interface ServerCardProps {
  server: ServerType;
}

export interface ServerCardListProps {
  servers: ServerType[];
}
