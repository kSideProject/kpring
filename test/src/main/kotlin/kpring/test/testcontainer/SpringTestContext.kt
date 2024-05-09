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

  class SpringDataJpaMySql : ContainerContextInitializer(
    image = "mysql:8.0",
    port = 3306,
    "MYSQL_ROOT_PASSWORD" to "testRoot1234",
    "MYSQL_USER" to "testUser",
    "MYSQL_PASSWORD" to "testUser1234",
    "MYSQL_DATABASE" to "testdb",
  ) {
    override fun properties(): Map<String, String> {
      return hashMapOf(
        "spring.datasource.url" to "jdbc:mysql://${this.container.host}:${this.container.firstMappedPort}/testdb",
        "spring.datasource.username" to "testUser",
        "spring.datasource.password" to "testUser1234",
        "spring.datasource.driver-class-name" to "com.mysql.cj.jdbc.Driver",
      )
    }
  }
}
