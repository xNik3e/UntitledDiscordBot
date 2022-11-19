package com.example.untitleddiscordbot.Models.DetailedGuild;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class DetailedGuildItem {

	@SerializedName("max_stage_video_channel_users")
	private int maxStageVideoChannelUsers;

	@SerializedName("preferred_locale")
	private String preferredLocale;

	@SerializedName("embed_channel_id")
	private String embedChannelId;

	@SerializedName("default_message_notifications")
	private int defaultMessageNotifications;

	@SerializedName("owner_id")
	private String ownerId;

	@SerializedName("widget_channel_id")
	private String widgetChannelId;

	@SerializedName("roles")
	private List<RolesItem> roles;

	@SerializedName("icon")
	private String icon;

	@SerializedName("description")
	private Object description;

	@SerializedName("system_channel_id")
	private String systemChannelId;

	@SerializedName("rules_channel_id")
	private Object rulesChannelId;

	@SerializedName("afk_timeout")
	private int afkTimeout;

	@SerializedName("features")
	private List<String> features;

	@SerializedName("afk_channel_id")
	private Object afkChannelId;

	@SerializedName("max_members")
	private int maxMembers;

	@SerializedName("stickers")
	private List<Object> stickers;

	@SerializedName("id")
	private String id;

	@SerializedName("hub_type")
	private Object hubType;

	@SerializedName("widget_enabled")
	private boolean widgetEnabled;

	@SerializedName("max_video_channel_users")
	private int maxVideoChannelUsers;

	@SerializedName("nsfw_level")
	private int nsfwLevel;

	@SerializedName("safety_alerts_channel_id")
	private Object safetyAlertsChannelId;

	@SerializedName("emojis")
	private List<EmojisItem> emojis;

	@SerializedName("nsfw")
	private boolean nsfw;

	@SerializedName("vanity_url_code")
	private Object vanityUrlCode;

	@SerializedName("max_presences")
	private Object maxPresences;

	@SerializedName("system_channel_flags")
	private int systemChannelFlags;

	@SerializedName("premium_progress_bar_enabled")
	private boolean premiumProgressBarEnabled;

	@SerializedName("banner")
	private String banner;

	@SerializedName("premium_subscription_count")
	private int premiumSubscriptionCount;

	@SerializedName("public_updates_channel_id")
	private Object publicUpdatesChannelId;

	@SerializedName("application_id")
	private Object applicationId;

	@SerializedName("embed_enabled")
	private boolean embedEnabled;

	@SerializedName("verification_level")
	private int verificationLevel;

	@SerializedName("discovery_splash")
	private Object discoverySplash;

	@SerializedName("explicit_content_filter")
	private int explicitContentFilter;

	@SerializedName("name")
	private String name;

	@SerializedName("mfa_level")
	private int mfaLevel;

	@SerializedName("premium_tier")
	private int premiumTier;

	@SerializedName("region")
	private String region;

	@SerializedName("splash")
	private String splash;

	public int getMaxStageVideoChannelUsers(){
		return maxStageVideoChannelUsers;
	}

	public String getPreferredLocale(){
		return preferredLocale;
	}

	public String getEmbedChannelId(){
		return embedChannelId;
	}

	public int getDefaultMessageNotifications(){
		return defaultMessageNotifications;
	}

	public String getOwnerId(){
		return ownerId;
	}

	public String getWidgetChannelId(){
		return widgetChannelId;
	}

	public List<RolesItem> getRoles(){
		return roles;
	}

	public String getIcon(){
		return icon;
	}

	public Object getDescription(){
		return description;
	}

	public String getSystemChannelId(){
		return systemChannelId;
	}

	public Object getRulesChannelId(){
		return rulesChannelId;
	}

	public int getAfkTimeout(){
		return afkTimeout;
	}

	public List<String> getFeatures(){
		return features;
	}

	public Object getAfkChannelId(){
		return afkChannelId;
	}

	public int getMaxMembers(){
		return maxMembers;
	}

	public List<Object> getStickers(){
		return stickers;
	}

	public String getId(){
		return id;
	}

	public Object getHubType(){
		return hubType;
	}

	public boolean isWidgetEnabled(){
		return widgetEnabled;
	}

	public int getMaxVideoChannelUsers(){
		return maxVideoChannelUsers;
	}

	public int getNsfwLevel(){
		return nsfwLevel;
	}

	public Object getSafetyAlertsChannelId(){
		return safetyAlertsChannelId;
	}

	public List<EmojisItem> getEmojis(){
		return emojis;
	}

	public boolean isNsfw(){
		return nsfw;
	}

	public Object getVanityUrlCode(){
		return vanityUrlCode;
	}

	public Object getMaxPresences(){
		return maxPresences;
	}

	public int getSystemChannelFlags(){
		return systemChannelFlags;
	}

	public boolean isPremiumProgressBarEnabled(){
		return premiumProgressBarEnabled;
	}

	public String getBanner(){
		return banner;
	}

	public int getPremiumSubscriptionCount(){
		return premiumSubscriptionCount;
	}

	public Object getPublicUpdatesChannelId(){
		return publicUpdatesChannelId;
	}

	public Object getApplicationId(){
		return applicationId;
	}

	public boolean isEmbedEnabled(){
		return embedEnabled;
	}

	public int getVerificationLevel(){
		return verificationLevel;
	}

	public Object getDiscoverySplash(){
		return discoverySplash;
	}

	public int getExplicitContentFilter(){
		return explicitContentFilter;
	}

	public String getName(){
		return name;
	}

	public int getMfaLevel(){
		return mfaLevel;
	}

	public int getPremiumTier(){
		return premiumTier;
	}

	public String getRegion(){
		return region;
	}

	public String getSplash(){
		return splash;
	}
}