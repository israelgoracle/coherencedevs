package com.telefonica.tran.lib.userdetails.domain;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;


public class OrganizationalRoleUserDetails extends PartyUserDetails {

	private static final long serialVersionUID = 1L;

	private String code;

	private Long organizationRoleSpecId;
	private BigDecimal organizationRoleId;

	private List<FunctionalRole> functionalRoles;

	public OrganizationalRoleUserDetails() {
		super();
	}

	public OrganizationalRoleUserDetails(String partyId, String code, String individualName, String tradingName,
			Long organizationRoleSpecId, BigDecimal organizationRoleId, Collection<GrantedAuthority> authorities,
			List<FunctionalRole> functionalRoles) {

		super(partyId, authorities);
		super.setIndividualName(individualName);
		super.setTradingName(tradingName);

		this.code = code;
		this.organizationRoleSpecId = organizationRoleSpecId;
		this.organizationRoleId = organizationRoleId;
		this.functionalRoles = functionalRoles;
	}

	
	@Deprecated
	public String getPartyName() {
		return super.getIndividualName();
	}

	public BigDecimal getOrganizationRoleId() {
		return organizationRoleId;
	}

	public void setOrganizationRoleId(BigDecimal organizationRoleId) {
		this.organizationRoleId = organizationRoleId;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public Long getOrganizationRoleSpecId() {
		return organizationRoleSpecId;
	}

	public void setOrganizationRoleSpecId(Long organizationRoleSpecId) {
		this.organizationRoleSpecId = organizationRoleSpecId;
	}

	public List<FunctionalRole> getFunctionalRoles() {
		return functionalRoles != null ? Collections.unmodifiableList(functionalRoles) : null;
	}

	public void setFunctionalRoles(List<FunctionalRole> functionalRoles) {
		this.functionalRoles = functionalRoles;
	}
}