import { useEffect, useState } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import { useLoginStore } from "@/store/useLoginStore";
import { validateAccessToken } from "@/api/auth";

const AuthLayout = () => {
  const navigate = useNavigate();
  const { accessToken, isLoggedIn } = useLoginStore();
  const [isLoading, setIsLoading] = useState(true);

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
      <div className="flex items-center justify-center min-h-screen"></div>
    );
  }

  return <Outlet />;
};

export default AuthLayout;
