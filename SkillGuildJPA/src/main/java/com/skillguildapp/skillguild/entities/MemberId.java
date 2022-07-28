package com.skillguildapp.skillguild.entities;

import java.io.Serializable;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class MemberId implements Serializable {
	
	private static final long serialVersionUID = 1l;
	
	@Column(name="guild_id")
	private int guildId;
	
	@Column(name="user_id")
	private int userId;

	public MemberId() {
		super();
	}

	public int getGuildId() {
		return guildId;
	}

	public void setGuildId(int guildId) {
		this.guildId = guildId;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public MemberId(int guildId, int userId) {
		super();
		this.guildId = guildId;
		this.userId = userId;
	}

	@Override
	public int hashCode() {
		return Objects.hash(guildId, userId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		MemberId other = (MemberId) obj;
		return guildId == other.guildId && userId == other.userId;
	}

	@Override
	public String toString() {
		return "MemberId [guildId=" + guildId + ", userId=" + userId + "]";
	}
	
	
	


}
