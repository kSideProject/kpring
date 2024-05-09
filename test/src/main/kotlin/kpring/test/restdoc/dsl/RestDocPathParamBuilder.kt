package kpring.test.restdoc.dsl

import org.springframework.restdocs.request.ParameterDescriptor
import org.springframework.restdocs.request.RequestDocumentation

class RestDocPathParamBuilder {
  val pathDescriptors = mutableListOf<ParameterDescriptor>()

  infix fun String.mean(description: String): ParameterDescriptor {
    val descriptor = RequestDocumentation.parameterWithName(this).description(description)
    pathDescriptors.add(descriptor)
    return descriptor
  }
}
