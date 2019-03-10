package com.prostoede.config;

import org.apache.commons.dbcp2.BasicDataSource;
import org.flywaydb.core.Flyway;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

/**
 *
 * @author prostoede
 */
@Configuration
@EnableTransactionManagement
@MapperScan(basePackages = "com.prostoede.dao.mapper", sqlSessionTemplateRef = "sqlSessionFactory")
public class DataBaseConfig implements TransactionManagementConfigurer {

    @Value("${jdbc.driverClassName.postgres}")
    private String driverClassName;

    @Value("${jdbc.url.postgres}")
    private String url;

    @Value("${jdbc.username}")
    private String userName;

    @Value("${jdbc.password}")
    private String password;

    @Value("${jdbc.maxIdle:10}")
    private Integer maxIdle;

    @Value("${jdbc.maxConnections:100}")
    private Integer maxTotal;

    @Bean(name = "dataSource", destroyMethod = "close")
    public BasicDataSource dataSource() {

	BasicDataSource bean = new BasicDataSource() {
	};
	bean.setDriverClassName(driverClassName);
	bean.setUrl(url);
	bean.setUsername(userName);
	bean.setPassword(password);
	bean.setDefaultAutoCommit(false);
	bean.setPoolPreparedStatements(true);
	bean.setConnectionProperties("autoReconnect=true");
	bean.setValidationQuery("SELECT 1;");
	bean.setMaxTotal(maxTotal);
	bean.setMaxIdle(maxIdle);

	return bean;
    }

    @Bean(name = "txManager")
    public DataSourceTransactionManager txManager(@Qualifier("dataSource") BasicDataSource dataSource) {
	return new DataSourceTransactionManager(dataSource);
    }

    @Override
    public PlatformTransactionManager annotationDrivenTransactionManager() {
	return txManager(dataSource());
    }

    @Bean(name = "sqlSessionFactory")
    public SqlSessionTemplate sqlSessionFactory(ResourcePatternResolver applicationContext) throws Exception {

	SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
	bean.setDataSource(dataSource());
	bean.setConfigLocation(applicationContext.getResource("classpath:com/prostoede/dao/config.xml"));

	return new SqlSessionTemplate(bean.getObject());
    }

    @Bean(name = "flyway", initMethod = "migrate")
    public Flyway flyway() {
	Flyway bean = new Flyway();
	bean.setDataSource(dataSource());
	bean.setLocations("classpath:/db/migration");
	bean.setBaselineOnMigrate(false);
	return bean;
    }

}
