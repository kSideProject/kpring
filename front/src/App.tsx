import { Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import AuthLayout from "./components/auth/AuthLayout";
import PrivateRoute from "./components/auth/PrivateRoute";
import Layout from "./components/layout/Layout";
import Home from "./pages/Home";
import Join from "./pages/Join";
import Login from "./pages/Login";
import ServerMapWithTheme from "./components/server/ServerMapWithTheme";

function App() {
  return (
    <div className="App">
      <Routes>
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<Login />} />
          <Route path="/join" element={<Join />} />
        </Route>
        <Route element={<Layout />}>
          <Route
            path="/"
            element={
              <PrivateRoute>
                <Home />
              </PrivateRoute>
            }
          />
          <Route
            path="/server/:serverId"
            element={
              <PrivateRoute>
                <ServerMapWithTheme />
              </PrivateRoute>
            }
          />

          <Route
            path="/:theme"
            element={
              <PrivateRoute>
                <ServerMapWithTheme />
              </PrivateRoute>
            }
          />
          <Route path="*" element={<Navigate replace to="/" />} />
        </Route>
      </Routes>
    </div>
  );
}

export default App;
