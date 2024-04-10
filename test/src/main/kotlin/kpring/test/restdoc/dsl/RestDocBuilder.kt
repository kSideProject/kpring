package kpring.test.restdoc.dsl

import org.springframework.restdocs.snippet.Snippet
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.web.reactive.server.WebTestClient

fun WebTestClient.BodyContentSpec.restDoc(
    identifier: String,
    config: RestDocBuilder.() -> Unit,
): WebTestClient.BodyContentSpec {
    val builder = RestDocBuilder()
    builder.config()
    val snippets = mutableListOf<Snippet>()
    if (builder.requestHeader != null) snippets.add(builder.requestHeader!!)
    if (builder.requestBody != null) snippets.add(builder.responseBody!!)
    if (builder.responseHeader != null) snippets.add(builder.responseHeader!!)
    if (builder.responseBody != null) snippets.add(builder.responseBody!!)

    if (snippets.isEmpty()) throw IllegalArgumentException("must input api spec at restDoc()")
    return this.consumeWith(WebTestClientRestDocumentation.document(identifier, *snippets.toTypedArray()))
}

class RestDocBuilder {

    var requestHeader: Snippet? = null
    var requestBody: Snippet? = null
    var responseHeader: Snippet? = null
    var responseBody: Snippet? = null

    fun request(config: RestDocRequestBuilder.() -> Unit) {
        val builder = RestDocRequestBuilder()
        builder.config()
        requestHeader = builder.headerSnippet
        requestBody = builder.bodySnippet
    }

    fun response(config: RestDocResponseBuilder.() -> Unit) {
        val builder = RestDocResponseBuilder()
        builder.config()
        responseHeader = builder.headerSnippet
        responseBody = builder.bodySnippet
    }
}