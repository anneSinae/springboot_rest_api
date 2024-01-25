package com.example.demo.utils;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;


@Configuration
public class WebConfig implements WebMvcConfigurer {
	
	// file upload
	@Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
		//"classpath:/upload/" classpath : builed resources directory by gradle, so need to build again
		registry.addResourceHandler("/resource/**").addResourceLocations("file:src/main/resources/upload/");
    }
	
	
	// datasource1
	@Bean
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSourceProperties dataSourceProperties1() {
        return new DataSourceProperties();
    }

    @Bean(name = "db1")
    @Primary
    @ConfigurationProperties("spring.datasource")
    public DataSource db1() {
        return dataSourceProperties1().initializeDataSourceBuilder().build();
    }

    @Bean(name = "jdbcTmpl1")
    @DependsOn("db1")
    public JdbcTemplate jdbcTmpl1(@Qualifier("db1") DataSource db1) {
        return new JdbcTemplate(db1);
    }

    @Bean(name = "namedJdbcTmpl1")
    @DependsOn("db1")
    public NamedParameterJdbcTemplate namedJdbcTmpl1(@Qualifier("db1") DataSource db1) {
        return new NamedParameterJdbcTemplate(db1);
    }

    
    // datasource2
    @Bean
    @ConfigurationProperties("spring.datasource2")
    public DataSourceProperties dataSourceProperties2() {
        return new DataSourceProperties();
    }

    @Bean(name = "db2")
    @ConfigurationProperties("spring.datasource2")
    public DataSource db2() {
        return dataSourceProperties2().initializeDataSourceBuilder().build();
    }

    @Bean(name = "jdbcTmpl2")
    @DependsOn("db2")
    public JdbcTemplate jdbcTmpl2(@Qualifier("db2") DataSource db2) {
        return new JdbcTemplate(db2);
    }

    @Bean(name = "namedJdbcTmpl2")
    @DependsOn("db2")
    public NamedParameterJdbcTemplate namedJdbcTmpl2(@Qualifier("db2") DataSource db2) {
        return new NamedParameterJdbcTemplate(db2);
    }
}