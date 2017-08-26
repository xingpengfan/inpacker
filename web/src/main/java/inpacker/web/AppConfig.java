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
public class AppConfig {

    @Autowired private Environment env;

    @Bean("igPackService")
    public PackService<IgPackConfig, IgPackItem> packService() {
        final File herokuDir = new File(env.getProperty("heroku.packs.dir.path"));
        final File localDir = new File(env.getProperty("local.packs.dir.path"));
        File packsDir;
        if (herokuDir.mkdirs())
            packsDir = herokuDir;
        else
            packsDir = localDir;
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
    public ItemRepository<IgPackConfig, IgPackItem> repository() {
        return new IgItemRepository();
    }

    @Bean
    public IgItemRepository igRepository() {
        return new IgItemRepository();
    }
}
