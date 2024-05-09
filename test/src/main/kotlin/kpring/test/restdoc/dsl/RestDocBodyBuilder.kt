package kpring.test.restdoc.dsl

import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class RestDocBodyBuilder {
  val bodyFields = mutableListOf<FieldDescriptor>()

  infix fun FieldDescriptor.mean(description: String) {
    bodyFields.add(this.description(description))
  }

  infix fun String.type(type: Any): FieldDescriptor = fieldWithPath(this).type(type)
}
