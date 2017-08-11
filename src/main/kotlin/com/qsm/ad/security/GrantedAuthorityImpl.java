package com.qsm.ad.security;

import org.springframework.security.core.GrantedAuthority;

/**
 * Created by TQ on 2017/8/11.
 */
public class GrantedAuthorityImpl implements GrantedAuthority {
    private String authority;

    public GrantedAuthorityImpl(String authority) {
        this.authority = authority;
    }

    public void setAuthority(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return this.authority;
    }
}