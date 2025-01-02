import React, { useState } from "react";
import useServers from "@/hooks/server/useServers";
import ServerCardItem from "../molecules/ServerCardItem";
import ServerFilterBar from "../organisms/ServerFilterBar";
import useServerCategories from "@/hooks/server/useCategories";

const ServerCards = () => {
  const { servers } = useServers();
  const [activeTab, setActiveTab] = useState<string>("all");
  const { categories } = useServerCategories();

  const filteredServers = servers?.filter((server) => {
    if (activeTab === "all") return true;
    return server.categories?.some((category) => category.id === activeTab);
  });

  const processedCategories =
    categories?.map((category) => ({
      id: category.id,
      name: category.name,
    })) || [];

  return (
    <React.Fragment>
      <ServerFilterBar
        tabs={processedCategories}
        activeTab={activeTab}
        onTabChange={setActiveTab}
      />
      <div className="flex flex-row flex-wrap gap-3">
        {filteredServers?.map((server) => (
          <ServerCardItem
            key={server.id}
            serverName={server.name}
            categories={server.categories || undefined}
          />
        ))}
      </div>
    </React.Fragment>
  );
};

export default ServerCards;
