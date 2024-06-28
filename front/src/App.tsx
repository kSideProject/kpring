import { Route, Routes } from "react-router-dom";
import "./App.css";
import Layout from "./components/Layout/Layout";
import { ServerMap } from "./components/Map/ServerMap";
import Home from "./pages/Home";
import Join from "./pages/Join";
import Login from "./pages/Login";
function App() {
  return (
    <div className="App">
      <Routes>
        <Route path="/login" element={<Login />} />
        <Route path="/join" element={<Join />} />
        <Route path="/" element={<Layout />}>
          <Route index element={<Home />} />
          <Route path="server/:serverId" element={<ServerMap />} />
        </Route>
      </Routes>
    </div>
  );
}
export default App;
