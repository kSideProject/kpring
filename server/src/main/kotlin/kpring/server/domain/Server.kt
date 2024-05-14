package kpring.server.domain

class Server(
  val id: String,
  val name: String,
  val users: MutableList<ServerUser>
) {
}
