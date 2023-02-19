package com.example.untitleddiscordbot.Models.DetailedChannels;

import com.google.gson.annotations.SerializedName;

public class PermissionOverwritesItem{

	@SerializedName("allow")
	private int allow;

	@SerializedName("deny")
	private int deny;

	@SerializedName("deny_new")
	private String denyNew;

	@SerializedName("id")
	private String id;

	@SerializedName("allow_new")
	private String allowNew;

	@SerializedName("type")
	private String type;

	public void setAllow(int allow){
		this.allow = allow;
	}

	public int getAllow(){
		return allow;
	}

	public void setDeny(int deny){
		this.deny = deny;
	}

	public int getDeny(){
		return deny;
	}

	public void setDenyNew(String denyNew){
		this.denyNew = denyNew;
	}

	public String getDenyNew(){
		return denyNew;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAllowNew(String allowNew){
		this.allowNew = allowNew;
	}

	public String getAllowNew(){
		return allowNew;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}
}