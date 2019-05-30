package com.excilys.cdb.persistence.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;
import java.util.Properties;
import java.util.TimeZone;

@Configuration
@ComponentScan(basePackages = { "com.excilys.cdb.persistence.dao",
	"com.excilys.cdb.persistence.mapper" }, excludeFilters = @ComponentScan.Filter(Configuration.class))
@EnableTransactionManagement
public class PersistenceConfig {

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(HikariConfig configuration) {
	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	return new HikariDataSource(configuration);
    }

    @Bean
    public HikariConfig hikariConfig() {
	return new HikariConfig("/datasource.properties");

    }

    @Bean
    public JpaVendorAdapter jpaVendorAdapter() {
	return new HibernateJpaVendorAdapter();
    }

    @Bean
    public LocalContainerEntityManagerFactoryBean entityManagerFactory(DataSource dataSource,
	    JpaVendorAdapter vendorAdapter, Properties jpaProperties) {
	LocalContainerEntityManagerFactoryBean em = new LocalContainerEntityManagerFactoryBean();
	em.setDataSource(dataSource);
	em.setPackagesToScan("com.excilys.cdb.persistence.entity");

	em.setJpaVendorAdapter(vendorAdapter);
	em.setJpaProperties(jpaProperties);

	return em;
    }

    @Bean
    public PlatformTransactionManager transactionManager(EntityManagerFactory emf) {
	JpaTransactionManager transactionManager = new JpaTransactionManager();
	transactionManager.setEntityManagerFactory(emf);
	return transactionManager;
    }

    @Bean
    public Properties jpaProperties() {
	Properties properties = new Properties();
		properties.setProperty("hibernate.hbm2ddl.auto", "validate");
	properties.setProperty("hibernate.dialect", "org.hibernate.dialect.MySQLDialect");
	return properties;
    }

}
