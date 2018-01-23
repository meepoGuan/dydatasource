package com.gx.dy.config;

import java.util.HashMap;
import java.util.Map;

import javax.sql.DataSource;

import org.apache.ibatis.io.VFS;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.gx.dy.plugin.PagePlugin;


/**
 * @author MeepoGuan
 *
 * <p>Description: </p>
 *
 * 2018年1月22日
 *
 */
@Configuration
@MapperScan(basePackages = "com.gx.dy.mapper")
public class MybatisConfig {

    /**
     * @return
     * @throws Exception
     * @Primary 该注解表示在同一个接口有多个实现类可以注入的时候，默认选择哪一个，而不是让@autowire注解报错
     */
    @Primary
    @Bean("tx1DataSource")
    @ConfigurationProperties(prefix = "datasource.tx1")
    public DataSource tx1DataSource() throws Exception {
        return DataSourceBuilder.create().build();
    }

    @Bean("tx2DataSource")
    @ConfigurationProperties(prefix = "datasource.tx2")
    public DataSource tx2DataSource() throws Exception {
        return DataSourceBuilder.create().build();
    }
    
    @Bean("tx3DataSource")
    @ConfigurationProperties(prefix = "datasource.tx3")
    public DataSource tx3DataSource() throws Exception {
        return DataSourceBuilder.create().build();
    }

    /**
     * @Qualifier 根据名称进行注入，通常是在具有相同的多个类型的实例的一个注入（例如有多个DataSource类型的实例）
     */
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource(@Qualifier("tx1DataSource") DataSource tx1DataSource,
                                               @Qualifier("tx2DataSource") DataSource tx2DataSource,
                                               @Qualifier("tx3DataSource") DataSource tx3DataSource) {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DataSourceEnum.tx1, tx1DataSource);
        targetDataSources.put(DataSourceEnum.tx2, tx2DataSource);
        targetDataSources.put(DataSourceEnum.tx3, tx3DataSource);
        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(tx1DataSource);// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }

    /**
     * 根据数据源创建SqlSessionFactory
     */
    @Bean
    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource,
                                               @Value("mybatis.typeAliasesPackage") String typeAliasesPackage,
                                               @Value("mybatis.mapperLocations") String mapperLocations) throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        VFS.addImplClass(SpringBootVFS.class);
        factoryBean.setDataSource(dynamicDataSource);// 指定数据源(这个必须有，否则报错)
        factoryBean.setTypeAliasesPackage("com.gx.dy.domain");
    	org.apache.ibatis.session.Configuration configuration = new org.apache.ibatis.session.Configuration();//设置可以返回值为null的字段
    	configuration.setCallSettersOnNulls(true);
    	ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
    	factoryBean.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
        PagePlugin plu = new PagePlugin();
        factoryBean.setPlugins(new Interceptor[]{plu});
        return factoryBean.getObject(); 
    }

    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(DynamicDataSource dataSource) throws Exception {
        return new DataSourceTransactionManager(dataSource);
    }
}
