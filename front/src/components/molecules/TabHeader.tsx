import TabButton from "../atoms/TabButton";

type TabHeaderProps = {
  tabs: { id: string; name: string }[];
  activeTab: string;
  onTabChange: (id: string) => void;
};

const TabHeader: React.FC<TabHeaderProps> = ({
  tabs,
  activeTab,
  onTabChange,
}) => {
  return (
    <div>
      {tabs.map((tab) => (
        <TabButton
          key={tab.id}
          label={tab.name}
          isActive={activeTab === tab.id}
          onClick={() => onTabChange(tab.id)}
        />
      ))}
    </div>
  );
};

export default TabHeader;
