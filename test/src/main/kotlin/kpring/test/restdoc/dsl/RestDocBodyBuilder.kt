package kpring.test.restdoc.dsl

import kpring.test.restdoc.json.JsonDataType
import org.springframework.restdocs.payload.FieldDescriptor
import org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath

class RestDocBodyBuilder {
  val bodyFields = mutableListOf<FieldDescriptor>()

  infix fun FieldDescriptor.mean(description: String): FieldDescriptor {
    val descriptor = this.description(description)
    bodyFields.add(descriptor)
    return descriptor
  }

  infix fun String.type(type: JsonDataType): FieldDescriptor = fieldWithPath(this).type(type.value)

  infix fun FieldDescriptor.optional(isOptional: Boolean): FieldDescriptor {
    return if (isOptional) this.optional() else this
  }
}
