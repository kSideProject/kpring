package kpring.test.testcontainer

class SpringTestContext {
    class SpringDataRedis : ContainerContextInitializer(
        image = "redis:alpine",
        port = 6379,
    ) {
        override fun properties(): Map<String, String> {
            return hashMapOf(
                "spring.data.redis.host" to this.container.host,
                "spring.data.redis.port" to this.container.firstMappedPort.toString(),
            )
        }
    }

}