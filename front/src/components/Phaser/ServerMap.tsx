import { forwardRef, useEffect, useLayoutEffect, useRef } from "react";
import { EventBus } from "./EventBus";
import { ServerMapProps, ServerMapTypes } from "../../types/server";
import EnterServer from "./main";

// 서버를 생성하고 관리하는 컴포넌트
// forwardRef를 사용해 부모 컴포넌트로부터 ref를 전달 받음
export const ServerMap = forwardRef<ServerMapTypes, ServerMapProps>(
  function ServerMap({ currentActiveScene }, ref) {
    // Phaser.Game 인스턴스를 저장하기 위한 ref 생성
    const mapRef = useRef<Phaser.Game | null>(null!);

    // 브라우저 화면 크기에 따라서 맵의 크기도 리사이즈 되는 함수
    const resizeMap = () => {
      if (mapRef.current) {
        const width = window.innerWidth;
        const height = window.innerHeight;
        mapRef.current.scale.resize(width, height);
      }
    };

    // DOM이 변경된 후, 브라우저 화면 재렌더링
    useLayoutEffect(() => {
      // serverRef.current가 null인 경우, EnterServer 함수를 호출하여 서버 초기화
      if (mapRef.current === null) {
        mapRef.current = EnterServer("map-container");

        // 'ref'는 함수로 받거나 객체로 받을 수 있음
        if (typeof ref === "function") {
          // 'ref'가 함수로 전달 되는 경우
          ref({ server: mapRef.current, scene: null });
        } else if (ref) {
          // 'ref'가 객체로 전달되는 경우
          ref.current = { server: mapRef.current, scene: null };
        }

        resizeMap();
        window.addEventListener("resize", resizeMap, false);
      }

      // 컴포넌트가 언마운트될 실행되는 함수
      return () => {
        if (mapRef.current) {
          mapRef.current.destroy(true);
          if (mapRef.current !== null) {
            mapRef.current = null;
          }
        }
        window.removeEventListener("resize", resizeMap);
      };
    }, [ref]);

    // 'currentActiveScene'과 'ref'가 변경될 때 마다 실행
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

    return <div id="map-container"></div>;
  }
);
