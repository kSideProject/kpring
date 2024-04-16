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

    fun field(config: RestDocFieldBuilder.() -> Unit) {
        val builder = RestDocFieldBuilder()
        builder.config()
        if (builder.queryDescriptors.isNotEmpty())
            snippets.add(RequestDocumentation.queryParameters(*builder.queryDescriptors.toTypedArray()))
    }

    fun snippet(snippet: Snippet){
        snippets.add(snippet)
    }
}