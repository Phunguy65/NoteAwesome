package com.note_awesome.core.repositories.note;

import com.note_awesome.NoteAwesomeEnv;
import jakarta.persistence.EntityManagerFactory;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "noteEntityManagerFactory", transactionManagerRef = "noteTransactionManager", basePackages = {"com.note_awesome.core.repositories.note"})
@EnableJpaAuditing
@EnableTransactionManagement
public class NoteJpaConfig {

    @Bean(name = "noteDataSource")
    protected DataSource getDataSource() {
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:h2:file:" + NoteAwesomeEnv.URL_DATABASE);
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        return dataSource;
    }

    private Map<String, String> additionalProperties() {
        var properties = new HashMap<String, String>();
        properties.put("hibernate.hbm2ddl.auto", "update");
        properties.put("hibernate.show_sql", "true");
        properties.put("hibernate.format_sql", "true");
        properties.put("hibernate.use_sql_comments", "true");

        return properties;
    }


    @Bean(name = "noteEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean noteEntityManagerFactory(final EntityManagerFactoryBuilder builder, @Qualifier("noteDataSource") DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.note_awesome.core.entities")
                .packages("com.note_awesome.core.entities.note")
                .persistenceUnit("note_awesome")
                .properties(additionalProperties())
                .build();
    }

    @Bean
    @Primary
    protected PlatformTransactionManager noteTransactionManager(@Qualifier("noteEntityManagerFactory") final EntityManagerFactory entityManagerFactory) {
        return new JpaTransactionManager(entityManagerFactory);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
