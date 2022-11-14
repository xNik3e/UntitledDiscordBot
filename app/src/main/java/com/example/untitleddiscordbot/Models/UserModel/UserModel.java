package com.example.untitleddiscordbot.Models.UserModel;

import com.google.gson.annotations.SerializedName;

public class UserModel{

	@SerializedName("flags")
	private int flags;

	@SerializedName("banner")
	private String banner;

	@SerializedName("avatar")
	private String avatar;

	@SerializedName("locale")
	private String locale;

	@SerializedName("discriminator")
	private String discriminator;

	@SerializedName("banner_color")
	private Object bannerColor;

	@SerializedName("premium_type")
	private int premiumType;

	@SerializedName("accent_color")
	private Object accentColor;

	@SerializedName("mfa_enabled")
	private boolean mfaEnabled;

	@SerializedName("id")
	private String id;

	@SerializedName("public_flags")
	private int publicFlags;

	@SerializedName("username")
	private String username;

	@SerializedName("avatar_decoration")
	private Object avatarDecoration;

	public void setFlags(int flags){
		this.flags = flags;
	}

	public int getFlags(){
		return flags;
	}

	public void setBanner(String banner){
		this.banner = banner;
	}

	public String getBanner(){
		return banner;
	}

	public void setAvatar(String avatar){
		this.avatar = avatar;
	}

	public String getAvatar(){
		return avatar;
	}

	public void setLocale(String locale){
		this.locale = locale;
	}

	public String getLocale(){
		return locale;
	}

	public void setDiscriminator(String discriminator){
		this.discriminator = discriminator;
	}

	public String getDiscriminator(){
		return discriminator;
	}

	public void setBannerColor(Object bannerColor){
		this.bannerColor = bannerColor;
	}

	public Object getBannerColor(){
		return bannerColor;
	}

	public void setPremiumType(int premiumType){
		this.premiumType = premiumType;
	}

	public int getPremiumType(){
		return premiumType;
	}

	public void setAccentColor(Object accentColor){
		this.accentColor = accentColor;
	}

	public Object getAccentColor(){
		return accentColor;
	}

	public void setMfaEnabled(boolean mfaEnabled){
		this.mfaEnabled = mfaEnabled;
	}

	public boolean isMfaEnabled(){
		return mfaEnabled;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
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
}