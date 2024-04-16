package kpring.test.restdoc.dsl

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation.parameterWithName

class RestDocQueryPathBuilder {
    val queryDescriptors = mutableListOf<ParameterDescriptor>()

    infix fun String.mean(description: String): ParameterDescriptor {
        val descriptor = parameterWithName(this).description(description)
        queryDescriptors.add(descriptor)
        return descriptor
    }
}