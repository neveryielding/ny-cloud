package com.nycloud.auth.model;


/**
 * @description:
 * @author: super.wu
 * @date: Created in 2018/7/6 0006
 * @modified By:
 * @version: 1.0
 **/
public class UserDetails {

    static final long serialVersionUID = -7588980448693010399L;

    private String userId;

    private String username;

    private String password;

    private boolean enabled = true;

    private String clientId;

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getPassword() {
        return this.password;
    }

    public String getUsername() {
        return this.username;
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }

    public boolean isCredentialsNonExpired() {
        return true;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public static class CustomUserDetailsBuilder {
        private UserDetails userDetails = new UserDetails();

        public CustomUserDetailsBuilder withUsername(String username) {
            userDetails.setUsername(username);
            return this;
        }

        public CustomUserDetailsBuilder withPassword(String password) {
            userDetails.setPassword(password);
            return this;
        }

        public CustomUserDetailsBuilder withClientId(String clientId) {
            userDetails.setClientId(clientId);
            return this;
        }

        public CustomUserDetailsBuilder withUserId(String userId) {
            userDetails.setUserId(userId);
            return this;
        }

        public UserDetails build() {
            return userDetails;
        }
    }
}
