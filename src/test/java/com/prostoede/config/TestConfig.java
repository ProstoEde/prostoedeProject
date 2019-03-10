package com.prostoede.config;

import com.opentable.db.postgres.embedded.EmbeddedPostgres;
import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 *
 * @author prostoede
 */
@Configuration
@PropertySource("classpath:config.properties")
@ComponentScan("com.prostoede")
@EnableTransactionManagement
@MapperScan(basePackages = "com.prostoede.dao.mapper", sqlSessionTemplateRef = "sqlSessionFactory")
public class TestConfig {

    @Bean(name = "pgsqlDataSource", destroyMethod = "close")
    public BasicDataSource getPgsqlDataSource() throws Exception {
	String jdbcUrl = EmbeddedPostgres.builder()
		.start()
		.getJdbcUrl("postgres", "postgres") + "&stringtype=unspecified";

	BasicDataSource bean = new BasicDataSource();
	bean.setDriverClassName("org.postgresql.Driver");
	bean.setUrl(jdbcUrl);
	bean.setUsername("postgres");
	bean.setPassword("postgres");
	bean.setDefaultAutoCommit(false);
	bean.setPoolPreparedStatements(true);
	bean.setConnectionProperties("autoReconnect=true");
	bean.setValidationQuery("SELECT 1;");
	return bean;
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionTemplate sqlSessionFactory(ResourcePatternResolver applicationContext) throws Exception {

	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	bean.setDataSource(getPgsqlDataSource());
	bean.setConfigLocation(applicationContext.getResource("classpath:com/prostoede/dao/config.xml"));

	return new SqlSessionTemplate(bean.getObject());
    }

    @Bean(name = "flyway", initMethod = "migrate")
    public Flyway flyway() throws Exception {
	Flyway bean = new Flyway();
	bean.setDataSource(getPgsqlDataSource());
	bean.setLocations("classpath:/db/migration/users");
	bean.setBaselineOnMigrate(false);
	return bean;
    }

}
