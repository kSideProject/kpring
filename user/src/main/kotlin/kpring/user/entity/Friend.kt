package kpring.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "tb_friend")
class Friend(
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private var id: Long? = null,
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "user_id")
  private var user: User,
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "friend_id")
  var friend: User,
  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  var requestStatus: FriendRequestStatus,
) {
  fun updateRequestStatus(requestStatus: FriendRequestStatus) {
    this.requestStatus = requestStatus
  }
}
