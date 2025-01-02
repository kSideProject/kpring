import React from "react";
import Tabs from "./Tabs";

type ServerFilterBarProps = {
  tabs: { id: string; name: string }[];
  activeTab: string;
  onTabChange: (id: string) => void;
};

const ServerFilterBar: React.FC<ServerFilterBarProps> = ({
  tabs,
  activeTab,
  onTabChange,
}) => {
  return (
    <div>
      <Tabs tabs={tabs} activeTab={activeTab} onTabChange={onTabChange} />
    </div>
  );
};

export default ServerFilterBar;
