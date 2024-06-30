package kpring.server.util

import kpring.server.domain.Category
import kpring.server.domain.Server
import kpring.server.domain.ServerHost
import kpring.server.domain.Theme

fun testServer(
  id: String? = "TEST_SERVER_ID",
  name: String = "TEST_SERVER_NAME",
  users: MutableSet<String> = mutableSetOf("TEST_USER_MEMBER_ID"),
  invitedUserIds: MutableSet<String> = mutableSetOf("TEST_INVITED_USER_ID"),
  theme: Theme = Theme.default(),
  categories: Set<Category> = setOf(Category.SERVER_CATEGORY1, Category.SERVER_CATEGORY2),
  hostId: String = "TEST_USER_HOST_ID",
  hostName: String = "TEST_USER_HOST_NAME",
): Server {
  // add host
  users.add(hostId)

  return Server(
    id = id,
    name = name,
    users = users,
    invitedUserIds = invitedUserIds,
    theme = theme,
    categories = categories,
    host = ServerHost(hostName, hostId),
  )
}
