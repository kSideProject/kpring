import { createBrowserRouter } from "react-router-dom";
import Layout from "./components/templates/Layout";
import Join from "./components/pages/Join";
import Login from "./components/pages/Login";
import PrivateRoute from "./utils/PrivateRoute";
import Home from "./components/pages/Home";
import AuthLayout from "./utils/AuthLayout";
import EditProfile from "./components/pages/EditProfile";
import Server from "./components/pages/Server";

const router = createBrowserRouter([
  {
    element: <AuthLayout />,
    children: [
      {
        path: "/login",
        element: <Login />,
      },
      {
        path: "/join",
        element: <Join />,
      },
    ],
  },
  {
    element: <Layout />,
    children: [
      {
        path: "/",
        element: (
          <PrivateRoute>
            <Home />
          </PrivateRoute>
        ),
      },
      {
        path: "/server/:serverId",
        element: (
          <PrivateRoute>
            <Server />
          </PrivateRoute>
        ),
      },

      {
        path: "/profile",
        element: (
          <PrivateRoute>
            <EditProfile />
          </PrivateRoute>
        ),
      },
      {
        path: "*",
        element: (
          <PrivateRoute>
            <Home />
          </PrivateRoute>
        ),
      },
    ],
  },
]);

export default router;
