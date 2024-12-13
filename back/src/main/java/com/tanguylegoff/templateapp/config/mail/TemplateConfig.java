package com.tanguylegoff.templateapp.config.mail;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.spring6.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

@Configuration
public class TemplateConfig {
  public static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";
  public static final String EMAIL_TEMPLATE_ROOT_FOLDER = "templates/";

  @Bean
  public ITemplateResolver templateResolver() {
    ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
    templateResolver.setPrefix(EMAIL_TEMPLATE_ROOT_FOLDER);
    templateResolver.setSuffix(".html");
    templateResolver.setTemplateMode("HTML");
    templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
    return templateResolver;
  }

  @Bean
  public ResourceBundleMessageSource i18nMailBundle() {
    ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
    messageSource.setBasename("notif-i18n");
    return messageSource;
  }

  @Bean
  public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, ResourceBundleMessageSource i18nMailBundle) {
    SpringTemplateEngine templateEngine = new SpringTemplateEngine();
    templateEngine.setTemplateResolver(templateResolver);
    templateEngine.setTemplateEngineMessageSource(i18nMailBundle);
    return templateEngine;
  }
}
