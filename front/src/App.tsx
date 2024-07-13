import { Navigate, Route, Routes } from "react-router-dom";
import "./App.css";
import AuthLayout from "./components/Auth/AuthLayout";
import Layout from "./components/Layout/Layout";
import { ServerMap } from "./components/Phaser/ServerMap";
import Home from "./pages/Home";
import Join from "./pages/Join";
import Login from "./pages/Login";
function App() {
  return (
    <div className="App">
      <Routes>
        <Route element={<AuthLayout />}>
          <Route path="/login" element={<Login />} />
          <Route path="/join" element={<Join />} />
        </Route>
        <Route element={<Layout />}>
          <Route path="/" element={<Home />} />
          <Route path="server/:serverId" element={<ServerMap />} />
          <Route path="*" element={<Navigate replace to="/" />} />
        </Route>
      </Routes>
    </div>
  );
}
export default App;
