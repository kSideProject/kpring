// 로그인
export type LoginFormValues = {
  email: string;
  password: string;
};

export type LoginFormValidateErrors = {
  emailError: string;
  passwordError: string;
};

// 회원가입
export type JoinFormValues = {
  nickname: string;
  email: string;
  password: string;
  passwordConfirm: string;
};

export type JoinFormValidateErrors = {
  nicknameError: string;
  emailError: string;
  passwordError: string;
  passwordConfirmError: string;
};

// 회원 검색
export type SearchUsers = {
  userId: string;
  email: string;
  username: string;
  file?: string;
};

export type SearchUserResultsResponse = {
  data: {
    users: SearchUsers[];
  };
};

//  유저 프로필 조회
export type UserProfile = {
  email: string;
  userId: string;
  username: string;
};

export type UserProfileResponse = {
  data: UserProfile;
};

//  친구 목록 조회
export type Friends = {
  friendId: string;
  username: string;
  email: string;
  imagePath?: string;
};

export type FriendsList = {
  userId: string;
  friends: Friends[];
};

export type FriendsListResponse = {
  data: FriendsList;
};

// 친구 신청
export type RequestFriendResponse = {
  data: {
    friendId: string;
  };
};

// 친구 신청 조회
export type FriendRequests = {
  userId: string;
  friendRequests: RequestedFriends[];
};

export type RequestedFriends = {
  friendId: string;
  username: string;
};

export type FriendRequestsResponseType = {
  data: FriendRequests;
};
