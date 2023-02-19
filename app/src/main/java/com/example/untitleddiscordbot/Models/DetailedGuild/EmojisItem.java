package com.example.untitleddiscordbot.Models.DetailedGuild;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class EmojisItem{

	@SerializedName("managed")
	private boolean managed;

	@SerializedName("roles")
	private List<Object> roles;

	@SerializedName("name")
	private String name;

	@SerializedName("available")
	private boolean available;

	@SerializedName("animated")
	private boolean animated;

	@SerializedName("id")
	private String id;

	@SerializedName("require_colons")
	private boolean requireColons;

	public boolean isManaged(){
		return managed;
	}

	public List<Object> getRoles(){
		return roles;
	}

	public String getName(){
		return name;
	}

	public boolean isAvailable(){
		return available;
	}

	public boolean isAnimated(){
		return animated;
	}

	public String getId(){
		return id;
	}

	public boolean isRequireColons(){
		return requireColons;
	}
}