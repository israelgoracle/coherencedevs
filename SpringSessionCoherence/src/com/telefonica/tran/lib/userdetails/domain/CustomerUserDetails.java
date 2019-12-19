package com.telefonica.tran.lib.userdetails.domain;

import java.util.List;

import org.springframework.security.core.GrantedAuthority;



public class CustomerUserDetails extends PartyUserDetails {

	private static final long serialVersionUID = 1L;

	private String prospectId;
	private String customerId;

	public CustomerUserDetails() {
		super();
	}

	public CustomerUserDetails(String partyId, List<GrantedAuthority> authorities) {
		super(partyId, authorities);
	}

	public CustomerUserDetails(String partyId, List<GrantedAuthority> authorities, String individualName,
			String tradingName, String customerId) {
		super(partyId, authorities);

		super.setIndividualName(individualName);
		super.setTradingName(tradingName);

		this.customerId = customerId;
	}


	public boolean isProspect() {
		return prospectId != null && customerId == null;
	}


	public boolean isCustomer() {
		return !isProspect();
	}

	public String getProspectId() {
		return prospectId;
	}

	public void setProspectId(String prospectId) {
		this.prospectId = prospectId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
}