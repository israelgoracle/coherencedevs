package com.telefonica.coco.lib.security.domain;

import java.math.BigDecimal;
import java.sql.Timestamp;



/**
 * The persistent class for the PARTP_PARTY_SESSION database table.
 * 
 */

public class PartySession {

	//
	private String sessionId;

	//
	private Timestamp audiTiCreation;

	private Timestamp audiTiUpdate;

	//
	private BigDecimal partyId;

	private Timestamp endValidity;

	//
	private Timestamp startValidity;

	//
	private BigDecimal creatorPartyId;

	private BigDecimal updaterPartyId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public Timestamp getAudiTiCreation() {
		return audiTiCreation;
	}

	public void setAudiTiCreation(Timestamp audiTiCreation) {
		this.audiTiCreation = audiTiCreation;
	}

	public Timestamp getAudiTiUpdate() {
		return audiTiUpdate;
	}

	public void setAudiTiUpdate(Timestamp audiTiUpdate) {
		this.audiTiUpdate = audiTiUpdate;
	}

	public BigDecimal getPartyId() {
		return partyId;
	}

	public void setPartyId(BigDecimal partyId) {
		this.partyId = partyId;
	}

	public Timestamp getEndValidity() {
		return endValidity;
	}

	public void setEndValidity(Timestamp endValidity) {
		this.endValidity = endValidity;
	}

	public Timestamp getStartValidity() {
		return startValidity;
	}

	public void setStartValidity(Timestamp startValidity) {
		this.startValidity = startValidity;
	}

	public BigDecimal getCreatorPartyId() {
		return creatorPartyId;
	}

	public void setCreatorPartyId(BigDecimal creatorPartyId) {
		this.creatorPartyId = creatorPartyId;
	}

	public BigDecimal getUpdaterPartyId() {
		return updaterPartyId;
	}

	public void setUpdaterPartyId(BigDecimal updaterPartyId) {
		this.updaterPartyId = updaterPartyId;
	}

	@Override
	public String toString() {
		return "PartySession [sessionId=" + sessionId + ", audiTiCreation=" + audiTiCreation + ", audiTiUpdate="
				+ audiTiUpdate + ", partyId=" + partyId + ", endValidity=" + endValidity + ", startValidity="
				+ startValidity + ", creatorPartyId=" + creatorPartyId + ", updaterPartyId=" + updaterPartyId + "]";
	}

}