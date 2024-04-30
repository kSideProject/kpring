package kpring.chat.global.exception

class GlobalException(private val errorCode: ErrorCode) : RuntimeException() {

    fun getErrorCode(): ErrorCode {
        return errorCode
    }
}