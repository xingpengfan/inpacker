package inpacker.web;

import inpacker.core.*;
import inpacker.instagram.IgItemRepository;
import inpacker.instagram.IgPackConfig;
import inpacker.instagram.IgPackItem;
import inpacker.px.PxItemRepository;
import inpacker.px.PxPackConfig;
import inpacker.px.PxPackItem;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;
@Configuration
public class AppConfig {

    @Autowired private Environment env;

    @Bean("igPackService")
    public PackService<IgPackConfig, IgPackItem> igPackService() {
        final File herokuDir = new File(env.getProperty("heroku.packs.dir.path"));
        final File localDir = new File(env.getProperty("local.packs.dir.path"));
        File packsDir;
        if (herokuDir.mkdirs())
            packsDir = herokuDir;
        else
            packsDir = localDir;
        Packer<IgPackItem> packer = "dir".equals(env.getProperty("packs.type")) ? igDirPacker() : igZipPacker();
        return new DefaultPackService<>(packsDir, igRepository(), packer);
    }

    @Bean("pxPackService")
    public PackService<PxPackConfig, PxPackItem> pxPackService() {
        final File herokuDir = new File(env.getProperty("heroku.packs.dir.path"));
        final File localDir = new File(env.getProperty("local.packs.dir.path"));
        File packsDir;
        if (herokuDir.mkdirs())
            packsDir = herokuDir;
        else
            packsDir = localDir;
        Packer<PxPackItem> packer = "dir".equals(env.getProperty("packs.type")) ? pxDirPacker() : pxZipPacker();
        return new DefaultPackService<>(packsDir, pxRepository(), packer);
    }

    @Bean("igZipPacker")
    public Packer<IgPackItem> igZipPacker() {
        return new ZipPacker<>();
    }

    @Bean("igDirPacker")
    public Packer<IgPackItem> igDirPacker() {
        return new DirPacker<>();
    }

    @Bean("pxZipPacker")
    public Packer<PxPackItem> pxZipPacker() {
        return new ZipPacker<>();
    }

    @Bean("pxDirPacker")
    public Packer<PxPackItem> pxDirPacker() {
        return new DirPacker<>();
    }

    @Bean("igRepository")
    public IgItemRepository igRepository() {
        return new IgItemRepository();
    }

    @Bean("pxRepository")
    public PxItemRepository pxRepository() {
        final String consumerKey = env.getProperty("500px.consumerkey");
        if (consumerKey == null || consumerKey.trim().isEmpty())
            throw new RuntimeException("invalid 500px consumer key");
        return new PxItemRepository(consumerKey);
    }

}
