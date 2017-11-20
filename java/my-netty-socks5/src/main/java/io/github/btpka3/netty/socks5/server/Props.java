package io.github.btpka3.netty.socks5.server;

import org.springframework.boot.context.properties.*;
import org.springframework.stereotype.*;

import java.util.*;

@Component
@ConfigurationProperties(prefix = "mns")
public class Props {

    private Integer port = 1080;

    private List<User> users;

    public static class User {
        private String username;
        private String password;

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
