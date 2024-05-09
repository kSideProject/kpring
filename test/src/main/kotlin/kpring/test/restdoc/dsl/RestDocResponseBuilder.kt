package kpring.test.restdoc.dsl

import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.snippet.Snippet

class RestDocResponseBuilder {
  val snippets = mutableListOf<Snippet>()

  fun header(config: RestDocHeaderBuilder.() -> Unit) {
    val builder = RestDocHeaderBuilder()
    builder.config()
    if (builder.headerFields.isNotEmpty()) {
      snippets.add(HeaderDocumentation.responseHeaders(*builder.headerFields.toTypedArray()))
    }
  }

  fun body(config: RestDocBodyBuilder.() -> Unit) {
    val builder = RestDocBodyBuilder()
    builder.config()
    if (builder.bodyFields.isNotEmpty()) {
      snippets.add(responseFields(*builder.bodyFields.toTypedArray()))
    }
  }

  fun snippet(snippet: Snippet) {
    snippets.add(snippet)
  }
}
