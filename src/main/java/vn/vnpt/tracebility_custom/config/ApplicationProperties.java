package vn.vnpt.tracebility_custom.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app")
public class ApplicationProperties {

    private String avatar;

    private SmsConfig smsConfig;

    private RedisConfig redisConfig;

    private FirebaseConfig firebaseConfig;

    @Data
    public static class SmsConfig {

        private String accountSID;

        private String authenToken;

        private String areaId;

        private String phone;
    }

    @Data
    public static class RedisConfig {

        private String host;

        private int port;
    }


    @Data
    public static class FirebaseConfig {

        private String ConfigurationFile;
    }

}
