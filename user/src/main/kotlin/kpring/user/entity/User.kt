package kpring.user.entity

import jakarta.persistence.*

@Entity
@Table(name = "tb_user")
class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Long? = null,

    @Column(nullable = false)
    var username: String,

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
        name = "user_followers",
        joinColumns = [JoinColumn(name = "user_id")],
        inverseJoinColumns = [JoinColumn(name = "follower_id")]
    )
    val followers: MutableSet<User> = mutableSetOf(),

    @ManyToMany(mappedBy = "followers", fetch = FetchType.LAZY)
    val followees: MutableSet<User> = mutableSetOf()

    // Other fields and methods...
) {
    fun addFollower(follower: User) {
        followers.add(follower)
        follower.followees.add(this)
    }

    fun removeFollower(follower: User) {
        followers.remove(follower)
        follower.followees.remove(this)
    }
}