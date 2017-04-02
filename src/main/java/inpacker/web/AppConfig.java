package inpacker.web;

import inpacker.core.Service;
import inpacker.core.InstagramUserProvider;
import inpacker.core.Repository;
import inpacker.core.Packer;
import inpacker.core.instagram.ServiceImpl;
import inpacker.core.instagram.InstagramUserProviderImpl;
import inpacker.core.instagram.IgRepository;
import inpacker.core.instagram.ZipPacker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AppConfig {

    @Bean
    public Service service() {
        return new ServiceImpl(mediaProvider(), packer());
    }

    @Bean
    public Repository mediaProvider() {
        return new IgRepository();
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
