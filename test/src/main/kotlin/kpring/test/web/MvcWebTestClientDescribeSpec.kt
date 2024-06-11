package kpring.test.web

import io.kotest.core.spec.style.DescribeSpec
import org.springframework.restdocs.ManualRestDocumentation
import org.springframework.restdocs.operation.preprocess.Preprocessors
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.servlet.client.MockMvcWebTestClient
import org.springframework.web.context.WebApplicationContext

abstract class MvcWebTestClientDescribeSpec(
  testMethodName: String,
  webContext: WebApplicationContext,
  body: DescribeSpec.(webTestClient: WebTestClient) -> Unit = {},
) : DescribeSpec({

    val restDocument = ManualRestDocumentation()

    val webTestClient: WebTestClient =
      MockMvcWebTestClient.bindToApplicationContext(webContext)
        .configureClient()
        .filter(
          WebTestClientRestDocumentation.documentationConfiguration(restDocument)
            .operationPreprocessors()
            .withRequestDefaults(Preprocessors.prettyPrint())
            .withResponseDefaults(Preprocessors.prettyPrint()),
        )
        .build()

    beforeSpec { restDocument.beforeTest(this.javaClass, testMethodName) }
    afterSpec { restDocument.afterTest() }

    body(webTestClient)
  })
