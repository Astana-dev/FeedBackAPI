package config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.hibernate4.Hibernate4Module;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Nurzhan on 03.10.2017.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"project.controller"})
public class WebAppConfig extends WebMvcConfigurerAdapter {

    /*@Bean
    public UrlBasedViewResolver setupViewResolver() {
        UrlBasedViewResolver resolver = new UrlBasedViewResolver();
        resolver.setPrefix("/pages/");
        resolver.setSuffix(".jsp");
        resolver.setViewClass(JstlView.class);
        return resolver;
    }*/

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(jacksonMessageConverter());
        converters.add(new StringHttpMessageConverter(Charset.forName("utf-8")));
        super.configureMessageConverters(converters);
    }
    public MappingJackson2HttpMessageConverter jacksonMessageConverter(){
        Jackson2ObjectMapperBuilder builder = new Jackson2ObjectMapperBuilder();
        builder.indentOutput(true).dateFormat(new SimpleDateFormat("yyyy-MM-dd"));

        MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();

        ObjectMapper mapper = builder.build();
        //Registering Hibernate4Module to support lazy objects
        mapper.registerModule(new Hibernate4Module());
        mapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);

        messageConverter.setObjectMapper(mapper);
        return messageConverter;

    }
}
