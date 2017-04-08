package inpacker.web;

import inpacker.core.*;
import inpacker.instagram.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;

@Configuration
public class AppConfig {

    private static final File packsDir = new File("/app/tmp/");

    @Bean("igPackService")
    public PackService<IgPackConfig, IgPackItem> packService() {
        return new DefaultPackService<>(packsDir, repository(), igZipPacker());
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
    public Repository<IgPackConfig, IgPackItem> repository() {
        return new IgRepository();
    }

    @Bean
    public IgRepository igRepository() {
        return new IgRepository();
    }
}
