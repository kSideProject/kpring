package kpring.server.domain

import kpring.core.global.exception.ServiceException
import kpring.server.error.ServerErrorCode

class Server(
  val id: String?,
  val name: String,
  val users: MutableSet<String> = mutableSetOf(),
  val invitedUserIds: MutableSet<String> = mutableSetOf(),
  val theme: Theme,
  val categories: Set<Category>,
  val host: ServerHost,
) {
  constructor(
    name: String,
    theme: String? = null,
    categories: List<String>? = null,
    hostName: String,
    hostId: String,
    users: MutableSet<String> = mutableSetOf(),
    invitedUserIds: MutableSet<String> = mutableSetOf(),
  ) : this(null, name, users, invitedUserIds, initTheme(theme), initCategories(categories), ServerHost(hostName, hostId))

  companion object {
    // -----------start : 초기화 로직 ------------

    /**
     * 서버의 테마 정보를 초기화합니다.
     * @param theme 서버의 테마 id를 나타냅니다. 테마 정보를 입력하지 않는다면 디폴트 값이 설정되며 디폴트 값은 [Theme.default] 입니다.
     * @throws ServiceException 요청한 테마 id가 잘못된 경우
     */
    private fun initTheme(theme: String?): Theme {
      if (theme == null) {
        return Theme.default()
      }
      try {
        return Theme.valueOf(theme)
      } catch (e: IllegalArgumentException) {
        throw ServiceException(ServerErrorCode.INVALID_THEME_ID)
      }
    }

    /**
     * 서버의 카테고리 정보를 초기화합니다.
     * @param categories 서버의 카테고리 id를 나타냅니다. 카테고리 정보를 입력하지 않는다면 카테고리가 없는 서버를 생성합니다.
     * @throws ServiceException 요청한 카테고리 id가 잘못된 경우
     */
    private fun initCategories(categories: List<String>?): Set<Category> {
      if (categories == null) {
        return setOf()
      }
      return categories.map {
        try {
          Category.valueOf(it)
        } catch (e: IllegalArgumentException) {
          throw ServiceException(ServerErrorCode.INVALID_CATEGORY_ID)
        }
      }.toSet()
    }
    // -----------end : 초기화 로직 --------------
  }

  private fun isInvited(userId: String): Boolean {
    return invitedUserIds.contains(userId)
  }

  /**
   * @param user 초대 받은 유저
   * @throws ServiceException 유저가 초대되지 않았을 경우
   */
  fun addUser(
    userId: String,
    name: String,
    imagePath: String,
  ): ServerProfile {
    if (this.isInvited(userId)) {
      invitedUserIds.remove(userId)
      users.add(userId)
    } else {
      throw ServiceException(ServerErrorCode.USER_NOT_INVITED)
    }
    return ServerProfile(null, userId, name, imagePath, this)
  }

  /**
   * @param userId 초대할 유저의 id
   * @throws ServiceException 이미 가입한 등록된 유저일 경우
   */
  fun registerInvitation(userId: String) {
    if (!users.contains(userId)) {
      invitedUserIds.add(userId)
    } else {
      throw ServiceException(ServerErrorCode.ALREADY_REGISTERED_USER)
    }
  }
}
