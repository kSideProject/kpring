import useUserProfile from "@/hooks/user/useUserProfile";
import EditProfileForm from "../organisms/EditProfileForm";
import Text from "../atoms/Text";
import Divider from "../atoms/Divider";
import Cookies from "js-cookie";
import { deleteUser } from "@/api/user";
import { useLoginStore } from "@/store/useLoginStore";

const EditProfile = () => {
  const { userProfile } = useUserProfile();
  const userId = Cookies.get("userId");
  const { accessToken } = useLoginStore();

  const handleDeleteUser = async () => {
    try {
      const response = await deleteUser(userId || "", accessToken);

      // TODO: 서버먼저 탈퇴 후 회원탈퇴 안내 알람 띄우기
      console.log(response, "회원탈퇴");
    } catch (error) {
      throw new Error("회원탈퇴 실패");
    }
  };

  return (
    <div className="flex flex-col items-center mt-10">
      <Text styles={`text-h4 font-bold mb-10 align-left`}>프로필 수정</Text>
      <EditProfileForm
        userData={{
          email: userProfile?.data.email || "",
          username: userProfile?.data.username || "",
        }}
      />
      <div className="w-96 my-7">
        <Divider style={`bg-lightGray `} />
      </div>
      <span
        className="text-p text-error font-bold cursor-pointer hover:underline"
        onClick={handleDeleteUser}>
        회원탈퇴
      </span>
    </div>
  );
};

export default EditProfile;
