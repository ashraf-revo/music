package org.revo.Configration;

import lombok.Getter;
import lombok.Setter;
import org.revo.Domain.User;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ashraf on 18/01/17.
 */
@ConfigurationProperties(prefix = "org.revo.app_env")
@Getter
@Setter
public class AppEnv {
    private List<User> users = new ArrayList<>();
    private String key = "org.revo.key";
    private String url;
    private Cloudinary cloudinary = new Cloudinary();
    private boolean reindex = true;

    public static class Cloudinary {
        private String name;
        private String key;
        private String secret;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getKey() {
            return key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getSecret() {
            return secret;
        }

        public void setSecret(String secret) {
            this.secret = secret;
        }
    }
}

