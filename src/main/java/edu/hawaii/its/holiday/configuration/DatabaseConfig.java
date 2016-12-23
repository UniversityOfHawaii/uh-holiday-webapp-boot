package edu.hawaii.its.holiday.configuration;

import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.jpa.HibernatePersistenceProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.annotation.PropertySources;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.util.Assert;

import edu.hawaii.its.holiday.util.Strings;

@Configuration
@EnableTransactionManagement
@PropertySources({
        @PropertySource("classpath:general.properties"),
        @PropertySource("classpath:database.properties"),
        @PropertySource(value = "file://${user.home}/.${user.name}-conf/myiam-overrides.properties",
                        ignoreResourceNotFound = true),
})
public class DatabaseConfig {

    @Value("${jdbc.url}")
    private String url;

    @Value("${jdbc.user}")
    private String username;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.driverClassName}")
    private String driverClassName;

    @Bean(name = "dataSource")
    public DataSource dataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);

        return dataSource;
    }

    @Bean
    public JpaTransactionManager transactionManager(EntityManagerFactory emf) {
        return new JpaTransactionManager(emf);
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();

        em.setPersistenceUnitName("holidayPersistenceUnit");
        em.setPersistenceProviderClass(HibernatePersistenceProvider.class);
        em.setPackagesToScan("edu.hawaii.its.holiday.type");

        em.setJpaVendorAdapter(new HibernateJpaVendorAdapter());
        em.setJpaProperties(jpaProperties());
        em.setDataSource(dataSource());

        return em;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Value("${db.hibernate.dialect}")
    private String hibernateDialect;

    @Value("${db.hibernate.hbm2ddl.auto}")
    private String hibernateHbm2ddlAuto;

    @Value("${db.hibernate.cache.provider_class}")
    private String hibernateCacheProviderClass;

    @Value("${db.hibernate.connection.shutdown}")
    private String hibernateConnectionShutdown;

    @Value("${db.hibernate.show_sql}")
    private String hibernateShowSql;

    protected Properties jpaProperties() {
        Properties properties = new Properties();
        properties.setProperty("hibernate.dialect", hibernateDialect);
        properties.setProperty("hibernate.hbm2ddl.auto", hibernateHbm2ddlAuto);
        properties.setProperty("hibernate.cache.provider_class", hibernateCacheProviderClass);
        properties.setProperty("hibernate.connection.shutdown", hibernateConnectionShutdown);
        properties.setProperty("hibernate.show_sql", hibernateShowSql);

        return properties;
    }

    @PostConstruct
    public void init() {
        Assert.hasLength(url, "property 'url' is required");
        Assert.hasLength(username, "property 'user' is required");
        Assert.hasLength(driverClassName, "property 'driverClassName' is required");
        Assert.hasLength(hibernateCacheProviderClass, "property 'hibernateCacheProviderClass' is required");

        System.out.println(Strings.fill('v', 88));
        System.out.println("url                        : " + url);
        System.out.println("username                   : " + username);
        System.out.println("driverClassName            : " + driverClassName);
        System.out.println("hibernateCacheProviderClass: " + hibernateCacheProviderClass);
        System.out.println("hibernateDialect           : " + hibernateDialect);
        System.out.println("hibernateShowSql           : " + hibernateShowSql);
        System.out.println(Strings.fill('^', 88));
    }

}