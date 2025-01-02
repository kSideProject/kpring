import { Navigate } from "react-router-dom";
import { useLoginStore } from "../store/useLoginStore";

interface PrivateRouteProps {
  children: JSX.Element;
}

const PrivateRoute = ({ children }: PrivateRouteProps) => {
  const isLoggedIn = useLoginStore((state) => state.isLoggedIn);
  return isLoggedIn() ? children : <Navigate to="/login" replace />;
};

export default PrivateRoute;
