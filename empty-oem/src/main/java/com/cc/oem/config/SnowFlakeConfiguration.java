package com.cc.oem.config;

import com.cc.oem.modules.config.utils.Snowflake;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 雪花算法配置
 */
@Configuration
public class SnowFlakeConfiguration {

    @Value("${snowflake.work-id:0}")
    private Long workId;

    @Value("${snowflake.data-center-id:0}")
    private Long dataCenterId;

    @Bean(name="Snowflake")
    public Snowflake snowflake(){
        return new Snowflake(workId,dataCenterId);
    }
}
