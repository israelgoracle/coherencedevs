package com.telefonica.coco.security.domain;

import java.io.Serializable;

public class PrincipalBase implements Serializable {


	private static final long serialVersionUID = 1L;

	public PrincipalBase() {		
	}

	public PrincipalBase(String partyId) {
		if ((partyId == null) || "".equals(partyId)) {
			throw new IllegalArgumentException("Cannot pass null or empty values to constructor");
		}
		this.partyId = partyId;
	}	
	
	/**
	 * Party identifier
	 */
	private String partyId;

	
	/**
	 * Authentication channel (origin) 
	 */
	private String provenience;
	
	
	/**
	 * Authentication scope
	 */
	private String scope;
	
	
	/**
	 * Organizational Role Id. NOTE: Only applicable for Telco ORGR
	 */
	private String orgRoleId;
		
	
	
	public final String getPartyId() {
		return partyId;
	}
	
	public final void setPartyId(String partyIdentifier) {
		this.partyId = partyIdentifier;
	}
	
	public final String getProvenience() {
		return provenience;
	}
	
	public final void setProvenience(String provenience) {
		this.provenience = provenience;
	}
	
	public final String getScope() {
		return scope;
	}
	
	public final void setScope(String scope) {
		this.scope = scope;
	}
	
	public final String getOrgRoleId() {
		return orgRoleId;
	}
		
	public final void setOrgRoleId(String orgRoleId) {
		this.orgRoleId = orgRoleId;
	}	
		

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof PrincipalBase))
			return false;
		PrincipalBase other = (PrincipalBase) obj;
		if (scope == null) {
			if (other.scope != null)
				return false;
		} else if (!scope.equals(other.scope))
			return false;
		if (orgRoleId == null) {
			if (other.orgRoleId != null)
				return false;
		} else if (!orgRoleId.equals(other.orgRoleId))
			return false;
		if (partyId == null) {
			if (other.partyId != null)
				return false;
		} else if (!partyId.equals(other.partyId))
			return false;
		return true;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((scope == null) ? 0 : scope.hashCode());
		result = prime * result + ((orgRoleId == null) ? 0 : orgRoleId.hashCode());
		result = prime * result + ((partyId == null) ? 0 : partyId.hashCode());
		return result;
	}	

	@Override
	public String toString() {
		return "PrincipalDetails [partyId=" + partyId + ", provenience=" + provenience + ", scope=" + scope
				+ ", orgRoleId=" + orgRoleId + "]";
	}	
	
}
