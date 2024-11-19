import AddServer from "./components/AddServer";
import CurrentUserAvatar from "./components/CurrentUserAvatar";

export const LeftSidebar = () => {
  return (
    <div className="max-w-20 h-screen flex flex-col items-center p-4 bg-slate-300">
      <div className="flex flex-col gap-3">
        <CurrentUserAvatar />
        <AddServer />
      </div>
      <div className="w-12 h-[0.5px] bg-black my-3"></div>
      <div className="text-sm">
        {/* TODO: 서버 고쳐지면 현재 유저의 서버 목록 가져오기 */}
        서버목록
      </div>
    </div>
  );
};
