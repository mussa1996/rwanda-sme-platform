package com.mussa.fintech.sme.config;


import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.UUID;

public class JwtUserDetails implements UserDetails {

    private final UUID merchantId;
    private final UUID merchantUserId;
    private final String role;

    public JwtUserDetails(UUID merchantId, UUID merchantUserId, String role) {
        this.merchantId = merchantId;
        this.merchantUserId = merchantUserId;
        this.role = role;
    }

    public UUID getMerchantId() {
        return merchantId;
    }

    public UUID getMerchantUserId() {
        return merchantUserId;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override public String getPassword() { return null; }
    @Override public String getUsername() { return merchantUserId.toString(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }
}

