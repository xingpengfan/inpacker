package inpacker.web;

import inpacker.core.*;
import inpacker.instagram.IgItemRepository;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
@PropertySource("classpath:application.properties")
public class AppConfig {

    @Autowired private Environment env;

    @Bean("igPackService")
    public PackService<IgPackConfig, IgPackItem> packService() {
        return new DefaultPackService<>(new File(env.getProperty("packs.dir.path")), repository(), igZipPacker());
    }

    @Bean("igZipPacker")
    public Packer<IgPackItem> igZipPacker() {
        return new ZipPacker<>();
    }

    @Bean("igDirPacker")
    public Packer<IgPackItem> igDirPacker() {
        return new DirPacker<>();
    }

    @Bean("igRepository")
    public ItemRepository<IgPackConfig, IgPackItem> repository() {
        return new IgItemRepository();
    }

    @Bean
    public IgItemRepository igRepository() {
        return new IgItemRepository();
    }
}
