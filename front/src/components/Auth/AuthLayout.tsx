import { useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { validateAccessToken } from "../../api/token";
import { useLoginStore } from "../../store/useLoginStore";

const AuthLayout = () => {
  const navigate = useNavigate();
  const { accessToken } = useLoginStore();
  const [isLoading, setIsLoading] = useState(true);

  useEffect(() => {
    const checkToken = async () => {
      if (accessToken) {
        const isValid = await validateAccessToken(accessToken);
        if (isValid) {
          navigate("/");
        }
      }
      setIsLoading(false);
    };
    checkToken();
  }, [accessToken, navigate]);

  if (isLoading) {
    return <div>로딩중</div>;
  }

  return <Outlet />;
};

export default AuthLayout;
