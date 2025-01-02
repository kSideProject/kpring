type TabButtonProps = {
  label: string;
  isActive: boolean;
  onClick: () => void;
};

const TabButton: React.FC<TabButtonProps> = ({ label, isActive, onClick }) => {
  return (
    <button
      onClick={onClick}
      className={`px-4 py-2 text-sm font-medium rounded-md transition ${
        isActive ? "bg-blue-500" : "bg-slate-100"
      }`}>
      {label}
    </button>
  );
};

export default TabButton;
