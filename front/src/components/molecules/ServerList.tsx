import useServers from "@/hooks/server/useServers";
import Avatar from "boring-avatars";
import Modal from "../organisms/Modal";
import useModal from "@/hooks/common/useModal";
import ServerInfo from "./ServerInfo";
import { useState } from "react";
import { GetServerType } from "@/types/server";
import { deleteServer } from "@/api/server";
import { useLoginStore } from "@/store/useLoginStore";
import { useNavigate } from "react-router";

const ServerList: React.FC = () => {
  const { accessToken } = useLoginStore();
  const { servers } = useServers();
  const { isOpen, closeModal, openModal } = useModal();
  const [selectedServer, setSelectedServer] = useState<GetServerType[]>();
  const navigate = useNavigate();

  console.log(servers);
  const openSelectedServer = (id: string) => {
    openModal("server");
    const filtered = servers?.filter((server) => server.id === id);
    setSelectedServer(filtered);
  };

  const enterServerHandler = (id: string) => {
    navigate(`server/${id}`);
    closeModal("server");
  };

  const deleteServerHandler = (id: string) => {
    deleteServer(id, accessToken);
    closeModal("server");
  };

  return (
    <div className="flex flex-col gap-3">
      {servers?.map((server) => (
        <Avatar
          key={server.id}
          name={server.name}
          variant="bauhaus"
          size={40}
          onClick={() => openSelectedServer(server.id)}
        />
      ))}

      {selectedServer?.map((server) => (
        <Modal
          key={server.id}
          title={server.name}
          isOpen={isOpen}
          closeModal={() => closeModal("server")}>
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
