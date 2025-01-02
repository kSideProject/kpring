import { createBrowserRouter } from "react-router-dom";
import Layout from "./components/templates/Layout";
import Join from "./components/pages/Join";
import Login from "./components/pages/Login";
import PrivateRoute from "./utils/PrivateRoute";
import Home from "./components/pages/Home";
import ServerMapWithTheme from "./components/Server/ServerMapWithTheme";
import AuthLayout from "./utils/AuthLayout";

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
            <ServerMapWithTheme />
          </PrivateRoute>
        ),
      },
      {
        path: "/:theme",
        element: (
          <PrivateRoute>
            <ServerMapWithTheme />
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
