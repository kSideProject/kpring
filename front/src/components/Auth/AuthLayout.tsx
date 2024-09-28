import { CircularProgress } from "@mui/material";
import { useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { validateAccessToken } from "../../api/token";
import { useLoginStore } from "../../store/useLoginStore";
import TokenRefreshAlert from "./TokenRefreshAlert";
import { useTokenExpirationCheck } from "../../utils/checkTokenExpiration";

const AuthLayout = () => {
  const navigate = useNavigate();
  const { accessToken, isLoggedIn } = useLoginStore();
  const [isLoading, setIsLoading] = useState(true);

  const showAlert = useTokenExpirationCheck(accessToken, 5 * 60 * 1000); // 만료 5분 전 알림

  useEffect(() => {
    const checkToken = async () => {
      if (isLoggedIn() && accessToken) {
        const isValid = await validateAccessToken(accessToken);
        if (isValid) {
          navigate("/");
        }
      }
      setIsLoading(false);
    };
    checkToken();
  }, [accessToken, navigate, isLoggedIn]);

  if (isLoading) {
    return (
      <div className="flex items-center justify-center min-h-screen">
        <CircularProgress color="primary" size={150} />
      </div>
    );
  }

  return (
    <>
      <Outlet />
      {showAlert && <TokenRefreshAlert open={showAlert} onClose={() => {}} />}
    </>
  );
};

export default AuthLayout;
