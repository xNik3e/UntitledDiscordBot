package com.example.untitleddiscordbot.Models;


public class AuthResponseModel{

	private String accessToken;
	private String refreshToken;
	private long expiresAt;
	private String scope;
	private String tokenType;

	public AuthResponseModel(String accessToken, String refreshToken, long expiresAt, String scope, String tokenType) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
		this.expiresAt = expiresAt;
		this.scope = scope;
		this.tokenType = tokenType;
	}

	public AuthResponseModel() {

		this.accessToken = "";
		this.refreshToken = "";
		this.expiresAt = 0;
		this.scope = "";
		this.tokenType = "";
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public void setExpiresAt(long expiresAt) {
		this.expiresAt = expiresAt;
	}

	public void setScope(String scope) {
		this.scope = scope;
	}

	public void setTokenType(String tokenType) {
		this.tokenType = tokenType;
	}

	public String getAccessToken(){
		return accessToken;
	}

	public String getRefreshToken(){
		return refreshToken;
	}

	public long getExpiresAt(){
		return expiresAt;
	}

	public String getScope(){
		return scope;
	}

	public String getTokenType(){
		return tokenType;
	}

	@Override
	public String toString() {
		return "AuthResponseModel{" +
				"accessToken='" + accessToken + '\'' +
				", refreshToken='" + refreshToken + '\'' +
				", expiresAt=" + expiresAt +
				", scope='" + scope + '\'' +
				", tokenType='" + tokenType + '\'' +
				'}';
	}
}