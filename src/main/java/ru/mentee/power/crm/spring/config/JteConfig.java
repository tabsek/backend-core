package ru.mentee.power.crm.spring.config;

import gg.jte.ContentType;
import gg.jte.TemplateEngine;
import gg.jte.resolve.DirectoryCodeResolver;
import gg.jte.springframework.boot.autoconfigure.JteProperties;
import gg.jte.springframework.boot.autoconfigure.JteViewResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

import java.nio.file.Path;

@Configuration
public class JteConfig {

    @Bean
    public TemplateEngine templateEngine() {
        DirectoryCodeResolver codeResolver = new DirectoryCodeResolver(Path.of("src/main/jte"));
        return TemplateEngine.create(codeResolver, ContentType.Html);
    }

    @Bean
    public JteViewResolver jteViewResolver(TemplateEngine templateEngine, JteProperties jteProperties) {
        JteViewResolver viewResolver = new JteViewResolver(templateEngine, jteProperties);
        viewResolver.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return viewResolver;
    }
}