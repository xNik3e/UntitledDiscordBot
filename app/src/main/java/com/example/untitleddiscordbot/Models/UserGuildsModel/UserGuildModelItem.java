package com.example.untitleddiscordbot.Models.UserGuildsModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserGuildModelItem{

	@SerializedName("owner")
	private boolean owner;

	@SerializedName("features")
	private List<Object> features;

	@SerializedName("permissions")
	private int permissions;

	@SerializedName("name")
	private String name;

	@SerializedName("icon")
	private String icon;

	@SerializedName("id")
	private String id;

	@SerializedName("permissions_new")
	private String permissionsNew;

	private boolean isBotAdded = false;

	public boolean isBotAdded() {
		return isBotAdded;
	}

	public void setBotAdded(boolean botAdded) {
		isBotAdded = botAdded;
	}

	public void setOwner(boolean owner){
		this.owner = owner;
	}

	public boolean isOwner(){
		return owner;
	}

	public void setFeatures(List<Object> features){
		this.features = features;
	}

	public List<Object> getFeatures(){
		return features;
	}

	public void setPermissions(int permissions){
		this.permissions = permissions;
	}

	public int getPermissions(){
		return permissions;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getName(){
		return name;
	}

	public void setIcon(String icon){
		this.icon = icon;
	}

	public String getIcon(){
		return icon;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPermissionsNew(String permissionsNew){
		this.permissionsNew = permissionsNew;
	}

	public String getPermissionsNew(){
		return permissionsNew;
	}

	public UserGuildModelItem() {
	}

	public UserGuildModelItem(String id) {
		this.id = id;
	}
}