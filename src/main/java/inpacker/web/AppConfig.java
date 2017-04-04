package inpacker.web;

import inpacker.core.*;
import inpacker.instagram.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AppConfig {

    private static final File packsDir = new File("/app/tmp/");

    @Bean("ig")
    public DefaultPackService<IgPackConfig, IgPackItem> defaultPackService() {
        return new DefaultPackService<>(packsDir, igRepository());
    }

    @Bean
    public Repository<IgPackConfig, IgPackItem> igRepository() {
        return new IgRepository();
    }

    @Bean
    public IgUserProvider instagramUserProvider() {
        return new IgUserProviderImpl();
    }
}
