package kpring.test.restdoc.dsl

import org.springframework.restdocs.headers.HeaderDocumentation
import org.springframework.restdocs.payload.PayloadDocumentation.responseFields
import org.springframework.restdocs.snippet.Snippet

class RestDocResponseBuilder {

    var headerSnippet: Snippet? = null
    var bodySnippet: Snippet? = null
    fun header(config: RestDocHeaderBuilder.() -> Unit) {
        val builder = RestDocHeaderBuilder()
        builder.config()
        if (builder.headerFields.isNotEmpty())
            headerSnippet = HeaderDocumentation.responseHeaders(*builder.headerFields.toTypedArray())
    }

    fun body(config: RestDocBodyBuilder.() -> Unit) {
        val builder = RestDocBodyBuilder()
        builder.config()
        if (builder.bodyFields.isNotEmpty())
            bodySnippet = responseFields(*builder.bodyFields.toTypedArray())
    }
}