package kpring.user.entity

import jakarta.persistence.*
import kpring.user.dto.request.UpdateUserProfileRequest

@Entity
@Table(name = "tb_user")
class User(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  var id: Long? = null,
  @Column(nullable = false)
  var username: String,
  @Column(nullable = false)
  var email: String,
  @Column(nullable = false)
  var password: String,
  var file: String?,
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = [CascadeType.ALL])
  val friends: MutableSet<Friend> = mutableSetOf(),
  // Other fields and methods...
) {
  fun requestFriend(user: User) {
    val friendRelation =
      Friend(
        user = this,
        friend = user,
        requestStatus = FriendRequestStatus.REQUESTED,
      )
    friends.add(friendRelation)
  }

  fun receiveFriendRequest(user: User) {
    val friendRelation =
      Friend(
        user = this,
        friend = user,
        requestStatus = FriendRequestStatus.RECEIVED,
      )
    friends.add(friendRelation)
  }

  fun removeFriendRelation(friendRelation: Friend) {
    friends.remove(friendRelation)
    friendRelation.friend.friends.removeIf { it.friend == this }
  }

  fun updateInfo(
    request: UpdateUserProfileRequest,
    newPassword: String?,
    file: String?,
  ) {
    request.email?.let { this.email = it }
    request.username?.let { this.username = it }
    newPassword?.let { this.password = it }

    if (this.file != null || file != null) {
      this.file = file
    }
  }
}
