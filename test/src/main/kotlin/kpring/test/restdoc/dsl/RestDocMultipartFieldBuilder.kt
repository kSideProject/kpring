package kpring.test.restdoc.dsl

import kpring.test.restdoc.json.JsonDataType
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class RestDocMultipartFieldBuilder {
  val fieldDescriptors = mutableListOf<FieldDescriptor>()

  infix fun String.type(type: JsonDataType): FieldDescriptor = fieldWithPath(this).type(type.value)

  infix fun FieldDescriptor.mean(description: String) {
    fieldDescriptors.add(this.description(description))
  }

  infix fun String.mean(description: String): FieldDescriptor {
    val descriptor: FieldDescriptor = fieldWithPath(this).description(description)
    fieldDescriptors.add(descriptor)
    return descriptor
  }
}
