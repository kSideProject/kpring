// ** React와 Phaser가 서로 이벤트를 주고 받을 수 있는 파일입니다. ** //
// ** 이벤트 통신에 중점을 둔 파일 ** //
// ** https://newdocs.phaser.io/docs/3.70.0/Phaser.Events.EventEmitter ** //

import { Events } from "phaser";

export const EventBus = new Events.EventEmitter();
