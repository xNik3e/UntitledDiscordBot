package com.example.untitleddiscordbot.Models.UserGuildsModel;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class UserGuildModel{

	@SerializedName("UserGuildModel")
	private List<UserGuildModelItem> userGuildModel;

	public void setUserGuildModel(List<UserGuildModelItem> userGuildModel){
		this.userGuildModel = userGuildModel;
	}

	public List<UserGuildModelItem> getUserGuildModel(){
		return userGuildModel;
	}
}