package com.example.demo.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
@EnableRedisRepositories
public class RedisConfig {
    @Value("${spring.REDIS_PORT}")
    private int PORT;

    @Bean
    public JedisConnectionFactory redisConnectionFactory() {
        RedisStandaloneConfiguration configuration = new RedisStandaloneConfiguration();
        configuration.setHostName("localhost");
        configuration.setPort(PORT);
        return new JedisConnectionFactory(configuration);
    }

    @Bean
    public RedisTemplate<String, Object> redisTemplateFactory() {
        RedisTemplate<String, Object> Template = new RedisTemplate<>();
        Template.setConnectionFactory(redisConnectionFactory());
        Template.setHashKeySerializer(new StringRedisSerializer());
        Template.setHashKeySerializer(new JdkSerializationRedisSerializer());
        Template.setKeySerializer(new StringRedisSerializer());
        Template.setValueSerializer(new JdkSerializationRedisSerializer());
        Template.setEnableTransactionSupport(true);
        Template.afterPropertiesSet();
        return Template;

    }


}
