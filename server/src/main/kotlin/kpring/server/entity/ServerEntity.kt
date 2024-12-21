package kpring.server.entity

import kpring.server.domain.Category
import kpring.server.domain.Server
import kpring.server.domain.ServerHost
import kpring.server.domain.Theme
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document

/**
 * @property id MongoDB의 ID를 나타냅니다.
 * @property name 서버의 이름을 나타냅니다.
 * @property users 서버에 속한 사용자의 아이디를 나타냅니다. 더 상세한 서버 유저에 대한 정보는 [ServerProfileEntity] 에서 찾을 수 있습니다.
 * @property hostId 서버를 관리자는 대표 유저의 아이디를 나타냅니다.
 * @property hostName 서버를 관리자는 대표 유저의 닉네임을 나타냅니다.
 * @property invitedUserIds 서버에 구성원은 아니지만 서버에 초대된 사용자의 아이디를 나타냅니다. 초대된 사용자는 서버에 가입할 수 있는 권한을 얻게 됩니다.
 * @property theme 서버의 테마 정보를 나타냅니다. 테마 정보를 입력하지 않는다면 디폴트 값이 설정되며 디폴트 값은 [Theme.default] 입니다.
 * @property categories 서버의 카테고리 정보를 나타냅니다. 카테고리 정보를 입력하지 않는다면 카테고리가 없는 서버를 생성합니다.
 */
@Document(collection = "server")
class ServerEntity(
  @Id
  val id: String?,
  var name: String,
  var hostId: String,
  var hostName: String,
  var users: MutableSet<String>,
  var invitedUserIds: MutableSet<String>,
  val theme: Theme,
  val categories: Set<Category>,
) {
  constructor(server: Server) : this(
    id = server.id,
    name = server.name,
    users = server.users,
    hostId = server.host.id,
    hostName = server.host.name,
    invitedUserIds = server.invitedUserIds,
    theme = server.theme,
    categories = server.categories,
  )

  fun toDomain(): Server {
    return Server(
      id = id,
      name = name,
      host = ServerHost(hostName, hostId),
      users = users,
      invitedUserIds = invitedUserIds.toMutableSet(),
      theme = theme,
      categories = categories,
    )
  }
}
