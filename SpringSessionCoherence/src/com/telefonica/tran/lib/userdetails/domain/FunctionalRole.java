package com.telefonica.tran.lib.userdetails.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashSet;

import org.springframework.security.core.GrantedAuthority;



public class FunctionalRole implements Serializable {

	private static final long serialVersionUID = 1L;

	private Collection<? extends GrantedAuthority> authorities = new HashSet<>();

	private BigDecimal functionalRoleId;
	private Long functionalRoleSpecId;

	public BigDecimal getFunctionalRoleId() {
		return functionalRoleId;
	}

	public void setFunctionalRoleId(BigDecimal functionalRoleId) {
		this.functionalRoleId = functionalRoleId;
	}

	public Long getFunctionalRoleSpecId() {
		return functionalRoleSpecId;
	}

	public void setFunctionalRoleSpecId(Long functionalRoleSpecId) {
		this.functionalRoleSpecId = functionalRoleSpecId;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}
}
