import { useNavigate } from "react-router";
import { useLoginStore } from "../../../store/useLoginStore";
import Button from "../../common/button/Button";
import useProfile from "../hooks/useProfile";
import Avatar from "boring-avatars";
import CurrentUserAvatar from "../../layout/components/leftSidebar/components/CurrentUserAvatar";

const UserProfile = () => {
  const token = localStorage.getItem("dicoTown_AccessToken");
  const user = useProfile("4", token);
  const { clearTokens } = useLoginStore();
  const navigate = useNavigate();

  const handleLogout = () => {
    clearTokens();
    localStorage.removeItem("dicoTown_AccessToken");
    navigate("/login");
  };

  return (
    <div className="flex flex-col justify-center items-center gap-3">
      <CurrentUserAvatar />
      <div>
        <p>user email:{user?.email}</p>
        <p>user name:{user?.username}</p>
        <p>user id:{user?.userId}</p>
      </div>
      <Button color="bg-sky-600" onClick={handleLogout}>
        로그아웃
      </Button>
    </div>
  );
};

export default UserProfile;
