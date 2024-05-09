package kpring.test.restdoc.dsl

import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper
import org.springframework.restdocs.snippet.Snippet
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.BodyContentSpec.restDoc(
  identifier: String,
  description: String,
  config: RestDocBuilder.() -> Unit,
): WebTestClient.BodyContentSpec {
  val builder = RestDocBuilder()
  builder.config()

  return this.consumeWith(
    WebTestClientRestDocumentationWrapper.document(
      identifier = identifier,
      description = description,
      snippets = builder.snippets.toTypedArray(),
    ),
  )
}

class RestDocBuilder {
  val snippets = mutableListOf<Snippet>()

  fun request(config: RestDocRequestBuilder.() -> Unit) {
    val builder = RestDocRequestBuilder()
    builder.config()
    snippets.addAll(builder.snippets)
  }

  fun response(config: RestDocResponseBuilder.() -> Unit) {
    val builder = RestDocResponseBuilder()
    builder.config()
    snippets.addAll(builder.snippets)
  }
}
