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
