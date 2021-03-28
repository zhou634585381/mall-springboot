package com.example.mall.config;

import com.baomidou.mybatisplus.annotation.DbType;
import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis-Plus的配置类
 *
 * @author 周瑜
 * @since 2021-03-24
 */
@Configuration
public class MybatisPlusConfig {

    /**
     * 分页插件配置，对相关数据库进行拦截并分页
     *
     * @return 分页拦截器
     */
    @Bean
    public MybatisPlusInterceptor paginationInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 使用不同的数据库，可以更改DbType.MYSQL的类型
        PaginationInnerInterceptor paginationInnerInterceptor = new PaginationInnerInterceptor(DbType.MYSQL);
        // 每页最多100条，可以更改，但不建议太大
        paginationInnerInterceptor.setMaxLimit(100L);
        paginationInnerInterceptor.setOverflow(true);
        interceptor.addInnerInterceptor(paginationInnerInterceptor);
        return interceptor;
    }

}
