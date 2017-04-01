package app.web;

import app.core.Inpacker;
import app.core.InstagramUserProvider;
import app.core.MediaProvider;
import app.core.Packer;
import app.core.impl.InpackerImpl;
import app.core.impl.InstagramUserProviderImpl;
import app.core.impl.MediaProviderImpl;
import app.core.impl.PackerImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Inpacker inpacker() {
        return new InpackerImpl(mediaProvider(), packer());
    }

    @Bean
    public MediaProvider mediaProvider() {
        return new MediaProviderImpl();
    }

    @Bean
    public Packer packer() {
        return new PackerImpl();
    }

    @Bean
    public InstagramUserProvider instagramUserProvider() {
        return new InstagramUserProviderImpl();
    }
}
