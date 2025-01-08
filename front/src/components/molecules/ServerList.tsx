import useServers from "@/hooks/server/useServers";
import Avatar from "boring-avatars";
import Modal from "../organisms/Modal";
import ServerInfo from "./ServerInfo";
import { useState } from "react";
import { GetServerType } from "@/types/server";
import { deleteServer } from "@/api/server";
import { useLoginStore } from "@/store/useLoginStore";
import { useNavigate } from "react-router";
import { useThemeStore } from "@/store/useThemeStore";
import useModalStore from "@/store/useModalStore";

const ServerList: React.FC = () => {
  const { accessToken } = useLoginStore();
  const { servers } = useServers();
  const [selectedServer, setSelectedServer] = useState<GetServerType[]>();
  const navigate = useNavigate();
  const { selectedTheme, setSelectedTheme } = useThemeStore();
  const { openModal, closeModal, modalType, isOpen } = useModalStore();

  const openSelectedServer = (id: string) => {
    openModal("showServer");
    const filtered = servers?.filter((server) => server.id === id);
    setSelectedServer(filtered);
  };

  const enterServerHandler = (id: string) => {
    navigate(`server/${id}`);
    closeModal();
    const server = servers?.find((server) => server.id === id);
    if (server) {
      setSelectedTheme(server.theme);
    }
  };

  const deleteServerHandler = (id: string) => {
    deleteServer(id, accessToken);
    closeModal();
  };

  return (
    <div className="flex flex-col ml-1 group-hover:items-start gap-3">
      {servers?.map((server) => (
        <div
          className="flex items-center gap-2 cursor-pointer"
          key={server.id}
          onClick={() => openSelectedServer(server.id)}>
          <Avatar name={server.name} variant="bauhaus" size={40} />
          <span className="hidden group-hover:inline-block text-small font-semibold">
            {server.name}
          </span>
        </div>
      ))}

      {isOpen &&
        modalType === "showServer" &&
        selectedServer?.map((server) => (
          <Modal key={server.id} title={server.name}>
            <ServerInfo
              hostName={server.hostName}
              serverId={server.id}
              categories={server.categories}
              onDelete={() => deleteServerHandler(server.id)}
              onEnter={() => enterServerHandler(server.id)}
            />
          </Modal>
        ))}
    </div>
  );
};

export default ServerList;
