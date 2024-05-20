import { forwardRef, useEffect, useLayoutEffect, useRef } from "react";
import { EventBus } from "./EventBus";
import { ServerProps, ServerTypes } from "../../types/server";
import EnterServer from "./main";

// 서버를 생성하고 관리하는 컴포넌트
// forwardRef를 사용해 부모 컴포넌트로부터 ref를 전달 받음
export const ServeScene = forwardRef<ServerTypes, ServerProps>(
  function ServerScene({ currentActiveScene }, ref) {
    // Phaser.Game 인스턴스를 저장하기 위한 ref 생성
    const serverRef = useRef<Phaser.Game | null>(null!);

    // DOM이 변경된 후, 브라우저 화면 재렌더링
    useLayoutEffect(() => {
      // serverRef.current가 null인 경우, 서버 초기화
      if (serverRef.current === null) {
        serverRef.current = EnterServer("server-container");

        if (typeof ref === "function") {
          ref({ server: serverRef.current, scene: null });
        } else if (ref) {
          ref.current = { server: serverRef.current, scene: null };
        }
      }

      // 컴포넌트가 언마운트될 때 서버 정리
      return () => {
        if (serverRef.current) {
          serverRef.current.destroy(true);
          if (serverRef.current !== null) {
            serverRef.current = null;
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
          ref({ server: serverRef.current, scene: scene_instance });
        } else if (ref) {
          ref.current = { server: serverRef.current, scene: scene_instance };
        }
      });
      return () => {
        EventBus.removeListener("current-scene-ready");
      };
    }, [currentActiveScene, ref]);

    return <div id="server-container"></div>;
  }
);
