package kpring.server.config

import kpring.core.auth.config.AuthClientConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(AuthClientConfig::class)
@Configuration
class CoreConfig {
}
