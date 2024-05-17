package kpring.server.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kpring.server.domain.ServerAuthority.*

class ServerAuthorityTest : FunSpec({

  test("요청 하는 권한이 같아면 true를 반환한다.") {
    INVITE.contains(INVITE) shouldBe true
    DELETE.contains(DELETE) shouldBe true
  }
})
