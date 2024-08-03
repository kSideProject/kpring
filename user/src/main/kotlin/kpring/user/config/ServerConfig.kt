package kpring.user.config

import kpring.core.server.config.ServerClientConfig
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Import

@Import(ServerClientConfig::class)
@Configuration
class ServerConfig
