package kpring.test.restdoc.json

enum class JsonDataType(val value: String) {
  Objects("Object"),
  Arrays("Array"),
  Strings("String"),
  Number("Number"),
  Integers("Integer"),
  Booleans("Boolean"),
  Null("Null"),
  Time("LocalDateTime"),
}
