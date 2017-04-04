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
        return new DefaultPackService<>(packsDir, igRepository(), igZipPacker());
    }

    @Bean("igZipPacker")
    public Packer<IgPackItem> igZipPacker() {
        return new ZipPacker<>();
    }

    @Bean("igDirPacker")
    public Packer<IgPackItem> igDirPacker() {
        return new DirPacker<>();
    }

    @Bean
    public Repository<IgPackConfig, IgPackItem> igRepository() {
        return new IgRepository();
    }
}
