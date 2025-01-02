import React, { useState } from "react";
import TabHeader from "../molecules/TabHeader";

type TabsProps = {
  tabs: { id: string; name: string }[];
  activeTab: string;
  onTabChange: (id: string) => void;
};

const Tabs: React.FC<TabsProps> = ({ tabs, activeTab, onTabChange }) => {
  return (
    <div>
      <TabHeader
        tabs={tabs.map(({ id, name }) => ({ id, name }))}
        activeTab={activeTab}
        onTabChange={onTabChange}
      />
    </div>
  );
};

export default Tabs;
