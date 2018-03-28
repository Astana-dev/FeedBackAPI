package config;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Properties;

/**
 * Created by Nurzhan on 11.10.2017.
 */
@Configuration
@EnableTransactionManagement
public class OrmConfig {

    // Hibernate properties
    @Value("${hibernate.dialect}")
    private String hibernateDialect;
    @Value("${hibernate.show_sql}")
    private String hibernateShowSql;
    @Value("${hibernate.hbm2ddl.auto}")
    private String hibernateHBM2DDLAuto;
//    @Value("${hibernate.hbm2ddl.auto}")
//    private String hibernateFormatSql;

    @Bean(destroyMethod = "")
    public DataSource dataSource() {
        Context context = null;
        try {
            context = new InitialContext();
            final DataSource dataSource = (DataSource) context.lookup("java:comp/env/jdbc/kapdb_ora");
            return dataSource;
        } catch (NamingException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Bean
    public Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put("hibernate.dialect", hibernateDialect);
        properties.put("hibernate.show_sql", hibernateShowSql);
//        properties.put("hibernate.format_sql", hibernateFormatSql);
//        properties.put("hibernate.hbm2ddl.auto", hibernateHBM2DDLAuto);
        properties.put("hibernate.enable_lazy_load_no_trans", true);
        return properties;
    }

    @Bean
    @SuppressWarnings("deprecation")
    public SessionFactory sessionFactory() {
        return new LocalSessionFactoryBuilder(dataSource())
                .scanPackages("entity")
                .addProperties(hibernateProperties())
                .buildSessionFactory();
    }

    @Bean
    public HibernateTransactionManager transactionManager(SessionFactory sessionFactory) {
        final HibernateTransactionManager htm = new HibernateTransactionManager();
        htm.setSessionFactory(sessionFactory);
        return htm;
    }

}
