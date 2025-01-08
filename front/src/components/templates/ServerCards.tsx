import React, { useState } from "react";
import useServers from "@/hooks/server/useServers";
import ServerCardItem from "../molecules/ServerCardItem";
import ServerFilterBar from "../organisms/ServerFilterBar";
import useServerCategories from "@/hooks/server/useCategories";
import Text from "../atoms/Text";

const ServerCards = () => {
  const { servers } = useServers();
  const [activeTab, setActiveTab] = useState<string>("all");
  const { categories } = useServerCategories();

  const filteredServers = servers?.filter((server) => {
    if (activeTab === "all") return true;
    return server.categories?.some((category) => category.id === activeTab);
  });

  const processedCategories = [
    { id: "all", name: "전체" },
    ...(categories?.map((category) => ({
      id: category.id,
      name: category.name,
    })) || []),
  ];
  return (
    <React.Fragment>
      <div className="flex flex-row justify-between items-center mb-5">
        <Text styles="text-h4 font-bold text-white">즐겨찾기 서버</Text>
        <ServerFilterBar
          tabs={processedCategories}
          activeTab={activeTab}
          onTabChange={setActiveTab}
        />
      </div>
      <div className="grid grid-cols-2 gap-5 md:grid-cols-3">
        {filteredServers?.map((server) => (
          <ServerCardItem
            id={server.id}
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
