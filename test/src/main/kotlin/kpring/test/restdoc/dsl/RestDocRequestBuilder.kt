package kpring.test.restdoc.dsl

import org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders
import org.springframework.restdocs.payload.PayloadDocumentation.requestFields
import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.snippet.Snippet

class RestDocRequestBuilder {

    var headerSnippet: Snippet? = null
    var bodySnippet: Snippet? = null
    var querySnippet: Snippet? = null
    fun header(config: RestDocHeaderBuilder.() -> Unit) {
        val builder = RestDocHeaderBuilder()
        builder.config()
        if (builder.headerFields.isNotEmpty())
            headerSnippet = requestHeaders(*builder.headerFields.toTypedArray())
    }

    fun body(config: RestDocBodyBuilder.() -> Unit) {
        val builder = RestDocBodyBuilder()
        builder.config()
        if (builder.bodyFields.isNotEmpty())
            bodySnippet = requestFields(*builder.bodyFields.toTypedArray())
    }
    fun field(config : RestDocFieldBuilder.() -> Unit) {
        val builder = RestDocFieldBuilder()
        builder.config()
        if (builder.queryDescriptors.isNotEmpty())
            querySnippet = RequestDocumentation.queryParameters(*builder.queryDescriptors.toTypedArray())
    }
}