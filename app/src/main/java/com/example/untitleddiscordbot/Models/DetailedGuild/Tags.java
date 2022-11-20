package com.example.untitleddiscordbot.Models.DetailedGuild;

import com.google.gson.annotations.SerializedName;

public class Tags{

	@SerializedName("bot_id")
	private String botId;

	@SerializedName("premium_subscriber")
	private Object premiumSubscriber;

	public String getBotId(){
		return botId;
	}

	public Object getPremiumSubscriber(){
		return premiumSubscriber;
	}
}