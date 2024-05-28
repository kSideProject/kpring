package kpring.test.web

class URLBuilder(
  private val url: String,
) {
  private val attributes: MutableList<Pair<String, String>> = mutableListOf()

  fun query(
    name: String,
    value: String,
  ): URLBuilder {
    attributes.add(name to value)
    return this
  }

  fun query(
    name: String,
    values: List<Any>,
  ): URLBuilder {
    return query(name, *values.toTypedArray())
  }

  fun query(
    name: String,
    vararg values: Any,
  ): URLBuilder {
    for (value in values) {
      attributes.add(name to value.toString())
    }
    return this
  }

  fun build(): String {
    return url + attributes.joinToString("&", "?") { (name, value) -> "$name=$value" }
  }
}
