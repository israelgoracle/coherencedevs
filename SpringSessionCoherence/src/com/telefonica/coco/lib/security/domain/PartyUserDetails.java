package com.telefonica.coco.lib.security.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

import org.springframework.security.core.GrantedAuthority;


import com.telefonica.coco.security.domain.PrincipalUserDetails;



public class PartyUserDetails extends PrincipalUserDetails {

	private static final long serialVersionUID = 1L;

	/**
	 * Se eliminará este atributo cuando todos los artefactos estén migrados a pom-Jee 1.3.0
	 */
	@Deprecated
	private String userSeed; 

	// Party Name
	private String individualName;
	private String tradingName;

	
	private Collection<GrantedAuthority> authorities = new ArrayList<>();


	public PartyUserDetails() {
		this.userSeed = UUID.randomUUID().toString();
	}

	public PartyUserDetails(String partyId, Collection<GrantedAuthority> authorities) {
		super(partyId);

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

}