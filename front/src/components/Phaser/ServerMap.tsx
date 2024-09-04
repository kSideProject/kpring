// ** 이 파일은 Phaser와 React를 연결해주는 역할을 하는 React 컴포넌트 입니다. ** //
// ** 게임을 초기화하고 실행하는 파일 ** //

import { forwardRef, useEffect, useLayoutEffect, useRef } from "react";
import StartGame from "./main";
import { EventBus } from "./EventBus";
import { ServerMapProps, ServerMapTypes } from "../../types/map";
import VideoCallBoxList from "../VideoCall/VideoCallBoxList";
import VideoCallToolBar from "../VideoCall/VideoCallToolBar";

export const ServerMap = forwardRef<ServerMapTypes, ServerMapProps>(
  function ServerMap({ currentActiveScene, selectedTheme }, ref) {
    const mapRef = useRef<Phaser.Game | null>(null!);

    useLayoutEffect(() => {
      if (mapRef.current === null) {
        mapRef.current = StartGame(selectedTheme, "map-container");
        console.log(selectedTheme);

        if (typeof ref === "function") {
          ref({ server: mapRef.current, scene: null });
        } else if (ref) {
          ref.current = { server: mapRef.current, scene: null };
        }
      }

      return () => {
        if (mapRef.current) {
          mapRef.current.destroy(true);
          if (mapRef.current !== null) {
            mapRef.current = null;
          }
        }
      };
    }, [ref]);

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
        <div className="absolute flex left-36 top-20">
          <VideoCallBoxList />
        </div>

        <div className="fixed bottom-[20px] left-1/2 -translate-x-1/3">
          <VideoCallToolBar />
        </div>
      </div>
    );
  }
);
