package kpring.test.restdoc.dsl

import org.springframework.restdocs.headers.HeaderDescriptor
import org.springframework.restdocs.headers.HeaderDocumentation.headerWithName

class RestDocHeaderBuilder {
    val headerFields = mutableListOf<HeaderDescriptor>()
    infix fun String.mean(description: String) {
        headerFields.add(headerWithName(this).description(description))
    }
}