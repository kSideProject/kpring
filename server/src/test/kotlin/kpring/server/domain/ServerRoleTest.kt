package kpring.server.domain

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe

class ServerRoleTest : FunSpec({

  test("owner는 모든 권한을 가지고 있다.") {
    // given
    val owner = ServerRole.OWNER
    val allAuth = ServerAuthority.entries.toTypedArray()

    // when & then
    allAuth.forEach {
      owner.contains(it) shouldBe true
    }
  }
})
