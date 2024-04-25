package kpring.core.global.dto.response


class ApiResponse<T> {
    var status: Int = 0
    var message: String = ""
    var data: T? = null

    constructor(status: Int, message: String, data: T) {
        this.status = status
        this.message = message
        this.data = data
    }

    constructor(status: Int, message: String) {
        this.status = status
        this.message = message
    }

    constructor(data: T) {
        this.data = data
    }
}