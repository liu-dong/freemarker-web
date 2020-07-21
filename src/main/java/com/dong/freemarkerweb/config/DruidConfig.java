package com.dong.freemarkerweb.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Druid连接池配置
 *
 * @author LD
 * @date 2019/12/30 11:24
 */
//@Configuration
//@EnableConfigurationProperties({DataSourcePropertiesConfig.class})
public class DruidConfig {
//
//    @Resource
//    private DataSourcePropertiesConfig properties;

//    /**
//     * druid数据源
//     *
//     * @return
//     */
//    @Bean
//    @ConditionalOnMissingBean //表示如果存在这个Bean,则不会创建
//    public DataSource druidDataSource() {
//        DruidDataSource druidDataSource = new DruidDataSource();
//        druidDataSource.setDriverClassName(properties.getDriverClassName());
//        druidDataSource.setUrl(properties.getUrl());
//        druidDataSource.setUsername(properties.getUsername());
//        druidDataSource.setPassword(properties.getPassword());
//        return druidDataSource;
//    }


}
