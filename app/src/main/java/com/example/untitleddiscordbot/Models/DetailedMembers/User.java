package com.example.untitleddiscordbot.Models.DetailedMembers;

import com.google.gson.annotations.SerializedName;

public class User{

	@SerializedName("id")
	private String id;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("public_flags")
	private int publicFlags;

	@SerializedName("username")
	private String username;

	@SerializedName("avatar_decoration")
	private Object avatarDecoration;

	@SerializedName("discriminator")
	private String discriminator;

	@SerializedName("bot")
	private boolean bot;

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setPublicFlags(int publicFlags){
		this.publicFlags = publicFlags;
	}

	public int getPublicFlags(){
		return publicFlags;
	}

	public void setUsername(String username){
		this.username = username;
	}

	public String getUsername(){
		return username;
	}

	public void setAvatarDecoration(Object avatarDecoration){
		this.avatarDecoration = avatarDecoration;
	}

	public Object getAvatarDecoration(){
		return avatarDecoration;
	}

	public void setDiscriminator(String discriminator){
		this.discriminator = discriminator;
	}

	public String getDiscriminator(){
		return discriminator;
	}

	public void setBot(boolean bot){
		this.bot = bot;
	}

	public boolean isBot(){
		return bot;
	}
}