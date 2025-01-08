// ** 이 파일은 Phaser와 React를 연결해주는 역할을 하는 React 컴포넌트 입니다. ** //
// ** 게임을 초기화하고 실행하는 파일 ** //

import { forwardRef, useEffect, useLayoutEffect, useRef } from "react";
import StartGame from "./main";
import { EventBus } from "./EventBus";
import { ServerMapProps, ServerMapTypes } from "../../types/map";
import VideoCallBoxList from "../VideoCall/VideoCallBoxList";
import VideoCallToolBar from "../VideoCall/VideoCallToolBar";
import { useThemeStore } from "@/store/useThemeStore";
import useUserProfile from "@/hooks/user/useUserProfile";
import ChatInput from "../organisms/ChatBox";
import ChatBox from "../organisms/ChatBox";

export const ServerMap = forwardRef<ServerMapTypes, ServerMapProps>(
  function ServerMap({ currentActiveScene }, ref) {
    const mapRef = useRef<Phaser.Game | null>(null!);
    const nickname = useUserProfile();

    console.log(nickname.userProfile?.data.username);

    const selectedTheme = useThemeStore((state) => state.selectedTheme);
    useLayoutEffect(() => {
      if (mapRef.current) {
        mapRef.current.destroy(true);
        mapRef.current = null;
      }

      mapRef.current = StartGame(
        selectedTheme,
        "map-container",
        nickname.userProfile?.data.username || ""
      );
      if (typeof ref === "function") {
        ref({ server: mapRef.current, scene: null });
      } else if (ref) {
        ref.current = { server: mapRef.current, scene: null };
      }

      return () => {
        if (mapRef.current) {
          mapRef.current.destroy(true);
          mapRef.current = null;
        }
      };
    }, [selectedTheme, ref]);

    useEffect(() => {
      EventBus.on("current-scene-ready", (scene_instance: Phaser.Scene) => {
        if (currentActiveScene && typeof currentActiveScene === "function") {
          currentActiveScene(scene_instance);
        }

        if (typeof ref === "function") {
          ref({ server: mapRef.current, scene: scene_instance });
        } else if (ref) {
          ref.current = { server: mapRef.current, scene: scene_instance };
        }
      });
      return () => {
        EventBus.removeListener("current-scene-ready");
      };
    }, [currentActiveScene, ref]);

    return (
      <div id="map-container">
        <div className="absolute">
          <ChatBox />
        </div>
        <div className="fixed bottom-[20px] left-1/2 -translate-x-1/3">
          <VideoCallToolBar />
        </div>
      </div>
    );
  }
);
