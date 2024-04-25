package kpring.chat.exception

class GlobalException(private val errorCode: ErrorCode) : RuntimeException() {

    fun getErrorCode(): ErrorCode {
        return errorCode
    }
}