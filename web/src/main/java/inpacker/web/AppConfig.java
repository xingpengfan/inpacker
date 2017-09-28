package inpacker.web;

import inpacker.core.*;
import inpacker.instagram.IgItemRepository;
import inpacker.instagram.IgPackConfig;
import inpacker.px.PxItemRepository;
import inpacker.px.PxPackConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;

@Configuration
public class AppConfig {

    @Autowired private Environment env;

    @Bean("igPackService")
    public PackService<IgPackConfig> igPackService() {
        final File herokuDir = new File(env.getProperty("heroku.packs.dir.path"));
        final File localDir = new File(env.getProperty("local.packs.dir.path"));
        File packsDir;
        if (herokuDir.mkdirs())
            packsDir = herokuDir;
        else
            packsDir = localDir;
        return new DefaultPackService<>(packsDir, igRepository(), packer());
    }

    @Bean("pxPackService")
    public PackService<PxPackConfig> pxPackService() {
        final File herokuDir = new File(env.getProperty("heroku.packs.dir.path"));
        final File localDir = new File(env.getProperty("local.packs.dir.path"));
        File packsDir;
        if (herokuDir.mkdirs())
            packsDir = herokuDir;
        else
            packsDir = localDir;
        return new DefaultPackService<>(packsDir, pxRepository(), packer());
    }

    @Bean
    public Packer packer() {
        if ("dir".equals(env.getProperty("packs.type")))
            return dirPacker();
        return zipPacker();
    }

    @Bean("zip")
    public Packer zipPacker() {
        return new ZipPacker();
    }

    @Bean("dir")
    public Packer dirPacker() {
        return new DirPacker();
    }

    @Bean("igRepository")
    public IgItemRepository igRepository() {
        final int maxPackSize = Integer.parseInt(env.getProperty("instagram.max-pack-size"));
        return new IgItemRepository(maxPackSize);
    }

    @Bean("pxRepository")
    public PxItemRepository pxRepository() {
        final String consumerKey = env.getProperty("500px.consumer-key");
        if (consumerKey == null || consumerKey.trim().isEmpty())
            throw new RuntimeException("invalid 500px consumer key");
        final int maxPackSize = Integer.parseInt(env.getProperty("500px.max-pack-size"));
        return new PxItemRepository(consumerKey, maxPackSize);
    }

}
