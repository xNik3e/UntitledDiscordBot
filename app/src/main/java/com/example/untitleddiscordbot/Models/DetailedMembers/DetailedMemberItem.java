package com.example.untitleddiscordbot.Models.DetailedMembers;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailedMemberItem{

	@SerializedName("joined_at")
	private String joinedAt;

	@SerializedName("nick")
	private Object nick;

	@SerializedName("premium_since")
	private Object premiumSince;

	@SerializedName("communication_disabled_until")
	private Object communicationDisabledUntil;

	@SerializedName("pending")
	private boolean pending;

	@SerializedName("roles")
	private List<String> roles;

	@SerializedName("flags")
	private int flags;

	@SerializedName("deaf")
	private boolean deaf;

	@SerializedName("mute")
	private boolean mute;

	@SerializedName("avatar")
	private Object avatar;

	@SerializedName("user")
	private User user;

	@SerializedName("is_pending")
	private boolean isPending;

	public void setJoinedAt(String joinedAt){
		this.joinedAt = joinedAt;
	}

	public String getJoinedAt(){
		return joinedAt;
	}

	public void setNick(Object nick){
		this.nick = nick;
	}

	public Object getNick(){
		return nick;
	}

	public void setPremiumSince(Object premiumSince){
		this.premiumSince = premiumSince;
	}

	public Object getPremiumSince(){
		return premiumSince;
	}

	public void setCommunicationDisabledUntil(Object communicationDisabledUntil){
		this.communicationDisabledUntil = communicationDisabledUntil;
	}

	public Object getCommunicationDisabledUntil(){
		return communicationDisabledUntil;
	}

	public void setPending(boolean pending){
		this.pending = pending;
	}

	public boolean isPending(){
		return pending;
	}

	public void setRoles(List<String> roles){
		this.roles = roles;
	}

	public List<String> getRoles(){
		return roles;
	}

	public void setFlags(int flags){
		this.flags = flags;
	}

	public int getFlags(){
		return flags;
	}

	public void setDeaf(boolean deaf){
		this.deaf = deaf;
	}

	public boolean isDeaf(){
		return deaf;
	}

	public void setMute(boolean mute){
		this.mute = mute;
	}

	public boolean isMute(){
		return mute;
	}

	public void setAvatar(Object avatar){
		this.avatar = avatar;
	}

	public Object getAvatar(){
		return avatar;
	}

	public void setUser(User user){
		this.user = user;
	}

	public User getUser(){
		return user;
	}

	public void setIsPending(boolean isPending){
		this.isPending = isPending;
	}

	public boolean isIsPending(){
		return isPending;
	}
}