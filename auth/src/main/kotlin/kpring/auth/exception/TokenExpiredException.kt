package kpring.auth.exception

class TokenExpiredException(message: String?, cause: Throwable?) : RuntimeException(message, cause) {
  constructor(cause: Throwable?) : this(null, cause)
  constructor(message: String?) : this(message, null)
}
