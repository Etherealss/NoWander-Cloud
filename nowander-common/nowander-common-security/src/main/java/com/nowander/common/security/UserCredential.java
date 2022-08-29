package com.nowander.common.security;

import cn.hutool.core.lang.Assert;
import cn.hutool.json.JSONObject;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;

/**
 * @author wtk
 * @date 2022-08-29
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserCredential implements UserDetails {
    String token;
    Integer userId;
    String username;
    String avatar;
    Set<SimpleGrantedAuthority> authorities;

    public UserCredential(String token, JSONObject tokenClaims) {
        Integer id = tokenClaims.get("id", Integer.class);
        String username = tokenClaims.get("username", String.class);
        String avatar = tokenClaims.get("avatar", String.class);
        Assert.notNull(id);
        Assert.notBlank(username);
        Assert.notBlank(avatar);
        this.token = token;
        this.userId = id;
        this.username = username;
        this.avatar = avatar;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
