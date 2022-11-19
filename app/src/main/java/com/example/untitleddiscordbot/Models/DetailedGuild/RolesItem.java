package com.example.untitleddiscordbot.Models.DetailedGuild;

import com.google.gson.annotations.SerializedName;

public class RolesItem{

	@SerializedName("color")
	private int color;

	@SerializedName("unicode_emoji")
	private Object unicodeEmoji;

	@SerializedName("icon")
	private Object icon;

	@SerializedName("flags")
	private int flags;

	@SerializedName("description")
	private Object description;

	@SerializedName("permissions_new")
	private String permissionsNew;

	@SerializedName("hoist")
	private boolean hoist;

	@SerializedName("tags")
	private Tags tags;

	@SerializedName("permissions")
	private int permissions;

	@SerializedName("managed")
	private boolean managed;

	@SerializedName("name")
	private String name;

	@SerializedName("mentionable")
	private boolean mentionable;

	@SerializedName("id")
	private String id;

	@SerializedName("position")
	private int position;

	public int getColor(){
		return color;
	}

	public Object getUnicodeEmoji(){
		return unicodeEmoji;
	}

	public Object getIcon(){
		return icon;
	}

	public int getFlags(){
		return flags;
	}

	public Object getDescription(){
		return description;
	}

	public String getPermissionsNew(){
		return permissionsNew;
	}

	public boolean isHoist(){
		return hoist;
	}

	public Tags getTags(){
		return tags;
	}

	public int getPermissions(){
		return permissions;
	}

	public boolean isManaged(){
		return managed;
	}

	public String getName(){
		return name;
	}

	public boolean isMentionable(){
		return mentionable;
	}

	public String getId(){
		return id;
	}

	public int getPosition(){
		return position;
	}
}