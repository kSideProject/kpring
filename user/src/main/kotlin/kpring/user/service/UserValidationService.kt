package kpring.user.service

import kpring.user.exception.ErrorCode
import kpring.user.exception.ExceptionWrapper
import kpring.user.repository.UserRepository
import org.springframework.stereotype.Service

@Service
class UserValidationService(
    private val userRepository: UserRepository
) {
    fun validateDuplicateEmail(email: String) {
        if (userRepository.existsByEmail(email)) {
            throw ExceptionWrapper(ErrorCode.ALREADY_EXISTS_EMAIL)
        }
    }

    fun validatePasswordMatch(password: String, passwordCheck: String) {
        if (password != passwordCheck) {
            throw ExceptionWrapper(ErrorCode.NOT_MATCH_PASSWORD)
        }
    }
}