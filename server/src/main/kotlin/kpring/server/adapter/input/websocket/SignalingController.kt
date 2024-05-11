package kpring.server.adapter.input.websocket

import org.slf4j.LoggerFactory
import org.springframework.messaging.handler.annotation.DestinationVariable
import org.springframework.messaging.handler.annotation.MessageMapping
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.messaging.handler.annotation.SendTo
import org.springframework.web.bind.annotation.RestController

@RestController
class SignalingController {
  // @Slf4j 사용 시, log 가 import 가 되지 않는 이슈로 아래와 같이 구현함
  private val log = LoggerFactory.getLogger(SignalingController::class.java)

  /**
   * offer 정보를 주고 받기 위한 websocket
   *
   * @param offer client 가 요청한 연결 offer : String
   * @param roomId roomId 값 : String
   * @param camKey  각 요청하는 캠의 key 값 : String
   * @return offer 값
   */
  @MessageMapping("/peer/offer/{camKey}/{roomId}")
  @SendTo("/topic/peer/offer/{camKey}/{roomId}")
  fun peerHandleOffer(
    @Payload offer: String,
    @DestinationVariable(value = "roomId") roomId: String,
    @DestinationVariable(value = "camKey") camKey: String,
  ): String {
    log.info("[OFFER] $camKey : $offer")
    return offer
  }

  /**
   * iceCandidate 정보를 주고 받기 위한 webSocket
   *
   * @param candidate ice candidate 정보
   * @param roomId roomId 값
   * @param camKey 각 요청하는 캠의 key 값
   * @return iceCandidate 정보
   */
  @MessageMapping("/peer/iceCandidate/{camKey}/{roomId}")
  @SendTo("/topic/peer/iceCandidate/{camKey}/{roomId}")
  fun peerHandleIceCandidate(
    @Payload candidate: String,
    @DestinationVariable(value = "roomId") roomId: String,
    @DestinationVariable(value = "camKey") camKey: String,
  ): String {
    log.info("[ICECANDIDATE] $camKey : $candidate")
    return candidate
  }

  /**
   * camKey 를 받기위해 신호를 보내는 webSocket
   *
   * @param answer information about any media already attached to the session, codecs and options supported by the browser, and any ICE candidates already gathered.
   * @param roomId roomId 값
   * @param camKey 각 요청하는 캠의 key 값
   * @return answer 값
   */
  @MessageMapping("/peer/answer/{camKey}/{roomId}")
  @SendTo("/topic/peer/answer/{camKey}/{roomId}")
  fun peerHandleAnswer(
    @Payload answer: String,
    @DestinationVariable(value = "roomId") roomId: String,
    @DestinationVariable(value = "camKey") camKey: String,
  ): String {
    log.info("[ANSWER] $camKey : $answer")
    return answer
  }

  /**
   * call 요청에 대한 message 응답
   *
   * @param message message 송신값
   * @return message
   */
  @MessageMapping("/call/key")
  @SendTo("/topic/call/key")
  fun callKey(
    @Payload message: String,
  ): String {
    log.info("[Key] : $message")
    return message
  }

  /**
   * 자신의 camKey 를 모든 연결된 세션에 보내는 webSocket
   *
   * @param message 송신 message 값
   * @return message
   */
  @MessageMapping("/send/key")
  @SendTo("/topic/send/key")
  fun sendKey(
    @Payload message: String,
  ): String {
    return message
  }
}
