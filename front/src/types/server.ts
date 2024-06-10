export interface ServerType {
  serverId: string;
  serverName: string;
  image: string;
  members: string[];
}

export interface ServerCardProps {
  server: ServerType;
}

export interface ServerCardListProps {
  servers: ServerType[];
}
