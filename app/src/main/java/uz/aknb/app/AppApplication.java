package uz.aknb.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import uz.aknb.app.config.ProjectConfig;
import uz.aknb.security.config.RsaKeyProperties;

@EnableJpaRepositories(basePackages = {ProjectConfig.REPOSITORY_PACKAGE})
@SpringBootApplication(scanBasePackages = {ProjectConfig.BASE_PACKAGE})
@EntityScan(basePackages = {ProjectConfig.ENTITY_PACKAGE})
@EnableConfigurationProperties(RsaKeyProperties.class)
@EnableTransactionManagement
@EnableJpaAuditing
@EnableAsync
public class AppApplication {

    public static void main(String[] args) {
        SpringApplication.run(AppApplication.class, args);
    }

}
