package com.note_awesome.models.repositories.note;

import jakarta.persistence.EntityManagerFactory;
import org.h2.Driver;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableJpaRepositories(entityManagerFactoryRef = "noteEntityManagerFactory",transactionManagerRef = "noteTransactionManager", basePackages = {"com.note_awesome.models.repositories.note"})
@EnableJpaAuditing
@EnableTransactionManagement
public class NoteJpaConfig {
    
    @Bean(name = "noteDataSource")
    protected DataSource getDataSource(){
        var dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(Driver.class.getName());
        dataSource.setUrl("jdbc:h2:file:./db/note_awesome");
        dataSource.setUsername("sa");
        dataSource.setPassword("");
        return dataSource;
    } 
    
    private Map<String, String> additionalProperties(){
        var properties = new HashMap<String, String>();
        properties.put("spring.jpa.hibernate.ddl-auto", "update");
        properties.put("spring.jpa.show-sql", "true");
        return properties;
    }
    
    
    @Bean(name = "noteEntityManagerFactory")
    @Primary
    public LocalContainerEntityManagerFactoryBean noteEntityManagerFactory(final EntityManagerFactoryBuilder builder, @Qualifier("noteDataSource") DataSource dataSource){
        return builder.dataSource(dataSource)
                .packages("com.note_awesome.models.entities")
                .persistenceUnit("note_awesome")
                .properties(additionalProperties())
                .build();
    }
    
    @Bean
    @Primary
    protected PlatformTransactionManager noteTransactionManager(@Qualifier("noteEntityManagerFactory") final EntityManagerFactory entityManagerFactory){
        return new JpaTransactionManager(entityManagerFactory);
    }
    
    
    
}
