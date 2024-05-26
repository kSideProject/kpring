package kpring.test.restdoc.dsl

import org.springframework.restdocs.request.RequestDocumentation
import org.springframework.restdocs.request.RequestPartDescriptor

class RestDocMultipartBuilder {
  val partDescriptors = mutableListOf<RequestPartDescriptor>()

  infix fun String.mean(description: String): RequestPartDescriptor {
    val descriptor = RequestDocumentation.partWithName(this).description(description)
    partDescriptors.add(descriptor)
    return descriptor
  }
}
