package kpring.test.restdoc.dsl

import org.junit.jupiter.api.Test


class MustBeFailTest {

  @Test
  fun test(){
    throw RuntimeException("Must be fail")
  }
}
