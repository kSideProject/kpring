const AUTH_URL = "/auth/api/v1";
const USER_URL = "/user/api/v1";
const SERVER_URL = "/server/api/v1";

export const AUTH_API = {
  POST_REQUEST: {
    accessToken: { url: `${AUTH_URL}/access_token`, method: "POST" },
    token: { url: `${AUTH_URL}/token`, method: "POST" },
    validation: { url: `${AUTH_URL}/validation`, method: "POST" },
  },

  DELETE_REQUEST: {
    deleteToken: (jwtToken: string) => ({
      url: `${AUTH_URL}/token/${jwtToken}`,
      method: "DELETE",
    }),
  },
};

export const USER_API = {
  GET_REQUEST: {
    fetchUsers: { url: `${USER_URL}/user`, method: "GET" },
    fetchProfile: (userId: string) => ({
      url: `${USER_URL}/user/${userId}`,
      method: "GET",
    }),
    fetchFriends: (userId: string) => ({
      url: `${USER_URL}/user/${userId}/friends`,
      method: "GET",
    }),
    fetchFriendRequests: (userId: string) => ({
      url: `${USER_URL}/user/${userId}/requests`,
      method: "GET",
    }),
  },

  POST_REQUEST: {
    login: { url: `${USER_URL}/login`, method: "POST" },
    logout: { url: `${USER_URL}/logout`, method: "POST" },
    join: { url: `${USER_URL}/user`, method: "POST" },
    requestFriend: (userId: string, friendId: string) => ({
      url: `${USER_URL}/user/${userId}/friend/${friendId}`,
      method: "POST",
    }),
  },

  PATCH_REQUEST: {
    editProfile: (userId: string) => ({
      url: `${USER_URL}/user/${userId}`,
      method: "PATCH",
    }),
    acceptFriendRequest: (userId: string, friendId: string) => ({
      url: `${USER_URL}/user/${userId}/friend/${friendId}`,
      method: "PATCH",
    }),
  },

  DELETE_REQUEST: {
    deleteAccount: (userId: string) => ({
      url: `${USER_URL}/user/${userId}`,
      method: "DELETE",
    }),
    deleteFriend: (userId: string, friendId: string) => ({
      url: `${USER_URL}/user/${userId}/friend/${friendId}`,
      method: "DELETE",
    }),
  },
};

export const SERVER_API = {
  GET_REQUEST: {
    fetchServerCategories: { url: `${SERVER_URL}/category`, method: "GET" },
    fetchServers: { url: `${SERVER_URL}/server`, method: "GET" },
    fetchSingleServer: (serverId: string) => ({
      url: `${SERVER_URL}/server/${serverId}`,
      method: "GET",
    }),
  },

  POST_REQUEST: {
    createServer: { url: `${SERVER_URL}/server`, method: "POST" },
  },

  PUT_REQUEST: {
    joinInServer: (serverId: string) => ({
      url: `${SERVER_URL}/server/${serverId}/user`,
      method: "POST",
    }),
    inviteToServer: (serverId: string, userId: string) => ({
      url: `${SERVER_URL}/server/${serverId}/invitation/${userId}`,
      method: "POST",
    }),
  },

  PATCH_REQUEST: {
    inheritHostRole: (serverId: string) => ({
      url: `${SERVER_URL}/server/${serverId}`,
      method: "PATCH",
    }),
  },

  DELETE_REQUEST: {
    deleteServer: (serverId: string) => ({
      url: `${SERVER_URL}/server/${serverId}`,
      method: "DELETE",
    }),
    leaveServer: (serverId: string) => ({
      url: `${SERVER_URL}/server/${serverId}/exit`,
      method: "DELETE",
    }),
  },
};
