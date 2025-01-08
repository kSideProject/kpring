type TabButtonProps = {
  label: string;
  isActive: boolean;
  onClick: () => void;
};

const TabButton: React.FC<TabButtonProps> = ({ label, isActive, onClick }) => {
  return (
    <button
      onClick={onClick}
      className={`px-4 py-2 text-p font-semibold rounded-md transition duration-300 hover:bg-secondary ${
        isActive
          ? "bg-primary text-white hover:text-primary "
          : "bg-quaternary  hover:text-primary"
      }`}>
      {label}
    </button>
  );
};

export default TabButton;
