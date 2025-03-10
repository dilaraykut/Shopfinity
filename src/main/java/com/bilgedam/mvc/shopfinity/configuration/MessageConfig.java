package com.bilgedam.mvc.shopfinity.configuration;

import java.util.Locale;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

@Configuration
public class MessageConfig implements WebMvcConfigurer{
	
	@Bean
	public ReloadableResourceBundleMessageSource messageSource() {
		ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();

		messageSource.setBasename("classpath:messages");
		messageSource.setDefaultEncoding("UTF-8");
		messageSource.setCacheSeconds(5);
		//messageSource.setDefaultLocale(new Locale("tr"));

		return messageSource;
	}
	
	// sayfanın dil seçeneğini değiştirmek için link tıklandığında gelen language paramtresine göre
		//sayfa dilinin değişmesini sağlayan metodlar
		@Bean
		public LocaleChangeInterceptor localeChangeInterceptor() {
			LocaleChangeInterceptor localeChangeInterceptor = new LocaleChangeInterceptor();
			localeChangeInterceptor.setParamName("language");
			return localeChangeInterceptor;
		}
		
		@Bean
		public LocaleResolver localeResolver() {
			final SessionLocaleResolver localeResolver = new SessionLocaleResolver();
			localeResolver.setDefaultLocale(new Locale("tr"));
			
			return localeResolver;
		}
		
		@Override
		public void addInterceptors(InterceptorRegistry registry) {
			registry.addInterceptor(localeChangeInterceptor());
		}
		
		// validation dependency projemize ekledikten sonra 
		// hata mesajlarının farklı dil desteklerine uygun verilebilmesi için
		// aşağıdaki metod sağlar 
		@Bean
		public LocalValidatorFactoryBean getValidator() {
		    LocalValidatorFactoryBean bean = new LocalValidatorFactoryBean();
		    bean.setValidationMessageSource(messageSource());
		    return bean;
		}

}
