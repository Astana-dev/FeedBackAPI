package config;

import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletRegistration.Dynamic;

/**
 * Created by Nurzhan on 12.10.2017.
 */
public class Initializer implements WebApplicationInitializer {

    private static final String FAMILY_SERVLET_NAME = "family";

    public void onStartup(ServletContext servletContext) {
        AnnotationConfigWebApplicationContext ctx = new AnnotationConfigWebApplicationContext();
        ctx.register(AppConfig.class);
        ctx.register(WebAppConfig.class);


        servletContext.addListener(new ContextLoaderListener(ctx));

        ctx.setServletContext(servletContext);

        Dynamic servlet = servletContext.addServlet(FAMILY_SERVLET_NAME, new DispatcherServlet(ctx));
        servlet.addMapping("/");
        servlet.setLoadOnStartup(1);

        servletContext.setInitParameter("defaultHtmlEscape", "true");

        FilterRegistration charEncodingFilterReq
                = servletContext.addFilter("CharacterEncodingFilter", CharacterEncodingFilter.class);
        charEncodingFilterReq.setInitParameter("encoding", "UTF-8");
        charEncodingFilterReq.setInitParameter("forceEncoding", "true");
        charEncodingFilterReq.addMappingForUrlPatterns(null,false,"/*");
    }
}
