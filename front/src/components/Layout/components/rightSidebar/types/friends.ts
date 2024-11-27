// 친구 목록 조회
export type UserFriendsListType = {
  userId: string;
  friends: FriendsType[];
};

export type FriendsType = {
  friendId: string;
  username: string;
  email: string;
  imagePath: string;
};

// 친구 요청 목록 조회
export type NewRequestedFriendsListType = {
  userId: string;
  friendRequests: FriendRequestsType[];
};

export type FriendRequestsType = {
  friendId: string;
  username: string;
};

export type GetRequestedFriendsResponseType = {
  data: NewRequestedFriendsListType;
};
