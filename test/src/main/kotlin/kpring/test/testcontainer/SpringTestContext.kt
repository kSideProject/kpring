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
            return mapOf(
                "spring.datasource.url" to "jdbc:mysql://${this.container.host}:${this.container.firstMappedPort}/testdb",
                "spring.datasource.username" to "testUser",
                "spring.datasource.password" to "testUser1234",
                "spring.datasource.driver-class-name" to "com.mysql.cj.jdbc.Driver",
            )
        }
    }

    class SpringDataMongo : ContainerContextInitializer(
        image = "mongo:latest",
        port = 27017,
        "MONGO_INITDB_ROOT_USERNAME" to "testRoot",
        "MONGO_INITDB_ROOT_PASSWORD" to "testRoot1234",
        "MONGO_INITDB_DATABASE" to "testdb",
    ) {
        override fun properties(): Map<String, String> {
            return mapOf(
                "spring.data.mongodb.host" to this.container.host,
                "spring.data.mongodb.port" to this.container.firstMappedPort.toString(),
                "spring.data.mongodb.database" to "testdb",
                "spring.data.mongodb.username" to "testRoot",
                "spring.data.mongodb.password" to "testRoot1234",
                "spring.data.mongodb.authentication-database" to "admin",
            )
        }
    }

}
