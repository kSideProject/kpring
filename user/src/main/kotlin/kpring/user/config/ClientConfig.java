package kpring.user.config;

import kpring.core.auth.client.AuthClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.support.RestClientAdapter;
import org.springframework.web.service.invoker.HttpServiceProxyFactory;

@Configuration
public class ClientConfig {

    @Value("${auth.url}")
    private String authUrl;

    @Bean
    AuthClient authClient() {
        var restClient = RestClient.builder()
                .baseUrl(authUrl)
                .build();

        var adapter = RestClientAdapter.create(restClient);
        var factory = HttpServiceProxyFactory.builderFor(adapter).build();

        return factory.createClient(AuthClient.class);
    }
}
