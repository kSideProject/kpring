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
      <div className="flex justify-center mb-5">
        <Tabs
          tabs={messageTab}
          activeTab={activeTab}
          onTabChange={setActiveTab}
        />
      </div>

      {activeTab === "message" ? <MessageList /> : <RequestedFriendsList />}
    </div>
  );
};

export default MessageSidebar;
