package test.web

import io.kotest.core.spec.style.FunSpec
import io.kotest.matchers.shouldBe
import kpring.test.web.URLBuilder

class URLBuilderTest : FunSpec({

  test("make url with url builder") {
    // given
    val url = "http://localhost:8080"
    val name = "test"
    val value1 = "value1"
    val value2 = "value2"
    val values = listOf("value3", "value4")

    // when
    val finalURL =
      URLBuilder(url)
        .query(name, value1)
        .query(name, value2)
        .query(name, *values.toTypedArray())
        .build()

    // then
    finalURL shouldBe "$url?$name=$value1&$name=$value2&$name=${values[0]}&$name=${values[1]}"
  }
})
