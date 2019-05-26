package com.excilys.cdb.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@ComponentScan("com.excilys.cdb.persistence.database")
@Import(PersistenceConfig.class)
public class PersistenceConfigTest {
}
