package com.telefonica.tran.lib.userdetails.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



public class PartyUserDetails implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String partyId;
	private String userSeed;

	// Party Name
	private String individualName;
	private String tradingName;

	private Collection<GrantedAuthority> authorities = new ArrayList<>();

	private String username = "";

	public PartyUserDetails() {
		this.userSeed = UUID.randomUUID().toString();
	}

	public PartyUserDetails(String partyId, Collection<GrantedAuthority> authorities) {
		if ((partyId == null) || "".equals(partyId)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}

		this.partyId = partyId;
		this.userSeed = UUID.randomUUID().toString();
		this.authorities = Collections.unmodifiableCollection(authorities);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public String getUserSeed() {
		return userSeed;
	}

	public String getPartyId() {
		return partyId;
	}

	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}

	public String getTradingName() {
		return tradingName;
	}

	public void setTradingName(String tradingName) {
		this.tradingName = tradingName;
	}

	public String getIndividualName() {
		return individualName;
	}

	public void setIndividualName(String individualName) {
		this.individualName = individualName;
	}

	public void setAuthorities(Collection<GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	/* Spring Security Field */

	@Override
	public String getUsername() {
		return username != null ? username : "";
	}

	/* Spring Security Non Used features */
	
	@Override
	public String getPassword() {
		return "";
	}

	
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	
	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((userSeed == null) ? 0 : userSeed.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;

		PartyUserDetails other = (PartyUserDetails) obj;
		if (userSeed == null) {
			if (other.userSeed != null)
				return false;
		} else if (!userSeed.equals(other.userSeed))
			return false;
		return true;
	}

}