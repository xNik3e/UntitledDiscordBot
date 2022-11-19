package com.example.untitleddiscordbot.Models.DetailedChannels;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailedChannelItem{

	@SerializedName("last_message_id")
	private String lastMessageId;

	@SerializedName("permission_overwrites")
	private List<PermissionOverwritesItem> permissionOverwrites;

	@SerializedName("nsfw")
	private boolean nsfw;

	@SerializedName("parent_id")
	private String parentId;

	@SerializedName("rate_limit_per_user")
	private int rateLimitPerUser;

	@SerializedName("name")
	private String name;

	@SerializedName("flags")
	private int flags;

	@SerializedName("guild_id")
	private String guildId;

	@SerializedName("topic")
	private String topic;

	@SerializedName("id")
	private String id;

	@SerializedName("position")
	private int position;

	@SerializedName("type")
	private int type;

	@SerializedName("bitrate")
	private int bitrate;

	@SerializedName("user_limit")
	private int userLimit;

	@SerializedName("rtc_region")
	private Object rtcRegion;

	@SerializedName("last_pin_timestamp")
	private String lastPinTimestamp;

	public void setLastMessageId(String lastMessageId){
		this.lastMessageId = lastMessageId;
	}

	public String getLastMessageId(){
		return lastMessageId;
	}

	public void setPermissionOverwrites(List<PermissionOverwritesItem> permissionOverwrites){
		this.permissionOverwrites = permissionOverwrites;
	}

	public List<PermissionOverwritesItem> getPermissionOverwrites(){
		return permissionOverwrites;
	}

	public void setNsfw(boolean nsfw){
		this.nsfw = nsfw;
	}

	public boolean isNsfw(){
		return nsfw;
	}

	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getParentId(){
		return parentId;
	}

	public void setRateLimitPerUser(int rateLimitPerUser){
		this.rateLimitPerUser = rateLimitPerUser;
	}

	public int getRateLimitPerUser(){
		return rateLimitPerUser;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setFlags(int flags){
		this.flags = flags;
	}

	public int getFlags(){
		return flags;
	}

	public void setGuildId(String guildId){
		this.guildId = guildId;
	}

	public String getGuildId(){
		return guildId;
	}

	public void setTopic(String topic){
		this.topic = topic;
	}

	public String getTopic(){
		return topic;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPosition(int position){
		this.position = position;
	}

	public int getPosition(){
		return position;
	}

	public void setType(int type){
		this.type = type;
	}

	public int getType(){
		return type;
	}

	public void setBitrate(int bitrate){
		this.bitrate = bitrate;
	}

	public int getBitrate(){
		return bitrate;
	}

	public void setUserLimit(int userLimit){
		this.userLimit = userLimit;
	}

	public int getUserLimit(){
		return userLimit;
	}

	public void setRtcRegion(Object rtcRegion){
		this.rtcRegion = rtcRegion;
	}

	public Object getRtcRegion(){
		return rtcRegion;
	}

	public void setLastPinTimestamp(String lastPinTimestamp){
		this.lastPinTimestamp = lastPinTimestamp;
	}

	public String getLastPinTimestamp(){
		return lastPinTimestamp;
	}
}