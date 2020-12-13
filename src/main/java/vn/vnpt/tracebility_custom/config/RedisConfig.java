/**
 * @author quangnv created on 5/27/2020
 */

package vn.vnpt.tracebility_custom.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
@EnableCaching
public class RedisConfig {

  @Autowired
  private ApplicationProperties applicationProperties;

  @Bean
  JedisConnectionFactory jedisConnectionFactory() {

    JedisConnectionFactory jedisConFactory = new JedisConnectionFactory();

    jedisConFactory.setHostName(applicationProperties.getRedisConfig().getHost());

    jedisConFactory.setPort(applicationProperties.getRedisConfig().getPort());

    return jedisConFactory;
  }

  @Bean
  public RedisTemplate<Object, Object> redisTemplate() {

    RedisTemplate<Object, Object> template = new RedisTemplate<>();

    template.setConnectionFactory(jedisConnectionFactory());

    return template;
  }

  @Bean
  CacheManager redisCache(
      @Qualifier("jedisConnectionFactory") RedisConnectionFactory connectionFactory) {

    return RedisCacheManager.builder(connectionFactory).build();

  }

}
