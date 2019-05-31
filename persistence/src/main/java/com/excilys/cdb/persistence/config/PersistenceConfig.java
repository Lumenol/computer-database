package com.excilys.cdb.persistence.config;

import java.util.Optional;
import java.util.Properties;
import java.util.TimeZone;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

@Configuration
@ComponentScan(basePackages = { "com.excilys.cdb.persistence.dao",
	"com.excilys.cdb.persistence.mapper" }, excludeFilters = @ComponentScan.Filter(Configuration.class))
@EnableTransactionManagement
@PropertySource("classpath:database.properties")
public class PersistenceConfig {

    private static final String HIBERNATE_SHOW_SQL = "hibernate.show_sql";
    private static final String HIBERNATE_DIALECT = "hibernate.dialect";
    private static final String HIBERNATE_HBM2DDL_AUTO = "hibernate.hbm2ddl.auto";

    @Bean(destroyMethod = "close")
    public HikariDataSource dataSource(HikariConfig configuration) {
	TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
	return new HikariDataSource(configuration);
    }

    @Bean
    public HikariConfig hikariConfig() {
	return new HikariConfig("/database.properties");

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
    public Properties jpaProperties(Environment environment) {
	Properties properties = new Properties();
	Optional.ofNullable(environment.getRequiredProperty(HIBERNATE_HBM2DDL_AUTO))
		.ifPresent(v -> properties.setProperty(HIBERNATE_HBM2DDL_AUTO, v));
	Optional.ofNullable(environment.getRequiredProperty(HIBERNATE_DIALECT))
		.ifPresent(v -> properties.setProperty(HIBERNATE_DIALECT, v));
	Optional.ofNullable(environment.getProperty(HIBERNATE_SHOW_SQL))
		.ifPresent(v -> properties.setProperty(HIBERNATE_SHOW_SQL, v));
	return properties;
    }

}
