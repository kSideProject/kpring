package kpring.test.testcontainer

import org.springframework.boot.test.util.TestPropertyValues
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.ConfigurableApplicationContext
import org.testcontainers.containers.GenericContainer

abstract class ContainerContextInitializer(
    image: String,
    port: Int
) : ApplicationContextInitializer<ConfigurableApplicationContext> {

    protected val container = GenericContainer(image)
        .withExposedPorts(port)

    private fun start() {
        this.container.start()
    }

    override fun initialize(applicationContext: ConfigurableApplicationContext) {
        start()
        val properties = this.properties()
        TestPropertyValues.of(properties).applyTo(applicationContext)
    }

    abstract fun properties(): Map<String, String>
}