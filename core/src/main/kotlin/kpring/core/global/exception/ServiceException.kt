package kpring.core.global.exception

class ServiceException(
  val errorCode: ErrorCode,
) : RuntimeException(errorCode.message())
