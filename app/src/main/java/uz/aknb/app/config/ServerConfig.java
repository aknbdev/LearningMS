package uz.aknb.app.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
public class ServerConfig {

    @Value("${server.mail}")
    private String mailFrom;

    @Value("${server.url}")
    private String serverUrl;
}
