package inpacker.web;

import inpacker.core.Service;
import inpacker.core.InstagramUserProvider;
import inpacker.core.Repository;
import inpacker.core.Packer;
import inpacker.core.impl.ServiceImpl;
import inpacker.core.impl.InstagramUserProviderImpl;
import inpacker.core.impl.IgRepository;
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
