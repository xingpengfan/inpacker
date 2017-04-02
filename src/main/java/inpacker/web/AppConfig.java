package inpacker.web;

import inpacker.core.Service;
import inpacker.core.InstagramUserProvider;
import inpacker.core.MediaProvider;
import inpacker.core.Packer;
import inpacker.core.impl.ServiceImpl;
import inpacker.core.impl.InstagramUserProviderImpl;
import inpacker.core.impl.MediaProviderImpl;
import inpacker.core.impl.ZipPacker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Service service() {
        return new ServiceImpl(mediaProvider(), packer());
    }

    @Bean
    public MediaProvider mediaProvider() {
        return new MediaProviderImpl();
    }

    @Bean
    public Packer packer() {
        return new ZipPacker();
    }

    @Bean
    public InstagramUserProvider instagramUserProvider() {
        return new InstagramUserProviderImpl();
    }
}
