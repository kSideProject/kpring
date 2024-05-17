import { BrowserRouter, Route, Routes } from "react-router-dom";
import "./App.css";
import Layout from "./components/Layout/Layout";
import { PhaserGame } from "./components/Phaser/PhaserGame";
import Home from "./pages/Home";
import Login from "./pages/Login";
function App() {
  return (
    <div className="App">
      <BrowserRouter>
        <Routes>
          <Route path="/login" element={<Login />} />
          <Route path="/" element={<Layout />}>
            <Route index element={<Home />} />
            <Route path="server/:serverId" element={<PhaserGame />} />
          </Route>
        </Routes>
      </BrowserRouter>
    </div>
  );
}
export default App;
