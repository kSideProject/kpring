package kpring.test.restdoc.dsl

import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.snippet.Snippet

class RestDocRequestBuilder {

    val snippets = mutableListOf<Snippet>()
    fun header(config: RestDocHeaderBuilder.() -> Unit) {
        val builder = RestDocHeaderBuilder()
        builder.config()
        if (builder.headerFields.isNotEmpty())
            snippets.add(requestHeaders(*builder.headerFields.toTypedArray()))
    }

    fun body(config: RestDocBodyBuilder.() -> Unit) {
        val builder = RestDocBodyBuilder()
        builder.config()
        if (builder.bodyFields.isNotEmpty())
            snippets.add(requestFields(*builder.bodyFields.toTypedArray()))
    }

    fun query(config: RestDocQueryParamBuilder.() -> Unit) {
        val builder = RestDocQueryParamBuilder()
        builder.config()
        if (builder.queryDescriptors.isNotEmpty())
            snippets.add(RequestDocumentation.queryParameters(*builder.queryDescriptors.toTypedArray()))
    }

    fun path(config: RestDocPathParamBuilder.() -> Unit) {
        val builder = RestDocPathParamBuilder()
        builder.config()
        if (builder.pathDescriptors.isNotEmpty())
            snippets.add(RequestDocumentation.pathParameters(*builder.pathDescriptors.toTypedArray()))
    }

    fun snippet(snippet: Snippet) {
        snippets.add(snippet)
    }
}