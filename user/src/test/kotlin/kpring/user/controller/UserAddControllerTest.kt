package kpring.user.controller

import com.fasterxml.jackson.databind.ObjectMapper
import kpring.user.dto.request.FriendsRequestDto
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.MediaType
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.post

@SpringBootTest
@AutoConfigureMockMvc
class UserAddControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper


    @Test
    @WithMockUser(username = "testUser", roles = ["USER"])
    fun `친구추가 성공케이스`() {
        val friendsRequestDto = FriendsRequestDto(
            userId = 1L,
            friendIds = listOf(2L, 3L)
        )

        mockMvc.post("/users/friends/add") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(friendsRequestDto)
        }.andExpect {
            status { isOk() }
            content {
//                jsonPath("$.status").value("SUCCESS")
//                jsonPath("$.message").value("Friends added successfully")
            }
        }
    }

    @Test
    @WithMockUser(username = "testUser", roles = ["USER"])
    fun `친구추가_실패케이스`() {
        val friendsRequestDto = FriendsRequestDto(
            userId = -1L, // 유효하지 않은 사용자 아이디
            friendIds = listOf(2L, 3L)
        )

        mockMvc.post("/users/friends/add") {
            contentType = MediaType.APPLICATION_JSON
            content = objectMapper.writeValueAsString(friendsRequestDto)
        }.andExpect {
            status { isBadRequest() }
            content {
//                jsonPath("$.status").value("FAILURE")
//                jsonPath("$.message").value("Invalid user id")
            }
        }
    }
}
