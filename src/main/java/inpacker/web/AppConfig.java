package inpacker.web;

import inpacker.core.*;
import inpacker.instagram.*;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.web.servlet.ErrorPage;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.io.File;

@Configuration
public class AppConfig {

    private static final File packsDir = new File("/app/tmp/");

    @Bean
    public WebMvcConfigurer webMvcConfigurer() {
        return new WebMvcConfigurerAdapter() {
            @Override
            public void addViewControllers(ViewControllerRegistry registry) {
                registry.addViewController("/").setViewName("index.html");
            }
        };
    }

    @Bean
    public EmbeddedServletContainerCustomizer containerCustomizer() {
        return container -> {
            final ErrorPage notFoundPage = new ErrorPage(HttpStatus.NOT_FOUND, "/404.html");
            container.addErrorPages(notFoundPage);
        };
    }

    @Bean("igPackService")
    public PackService<IgPackConfig, IgPackItem> packService() {
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
