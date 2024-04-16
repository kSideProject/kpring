package kpring.user.repository

import kpring.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long>{
    fun findByIdIn(ids: List<Long>): List<User>
}