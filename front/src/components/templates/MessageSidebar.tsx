import { useState } from "react";
import Tabs from "../organisms/Tabs";
import MessageList from "../organisms/MessageList";
import RequestedFriendsList from "../organisms/RequestedFriendsList";

const messageTab = [
  { id: "message", name: "메세지" },
  { id: "new frriends", name: "새로운 친구요청" },
];

const MessageSidebar = () => {
  const [activeTab, setActiveTab] = useState<string>("message");

  return (
    <div>
      <Tabs
        tabs={messageTab}
        activeTab={activeTab}
        onTabChange={setActiveTab}
      />

      {activeTab === "message" ? <MessageList /> : <RequestedFriendsList />}
    </div>
  );
};

export default MessageSidebar;
