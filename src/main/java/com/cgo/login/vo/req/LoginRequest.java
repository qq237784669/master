package com.cgo.login.vo.req;

import java.io.Serializable;

public class LoginRequest implements Serializable {
    private String userId;
    private String userType;
    private String password;
    private String imei;
    private Integer appVersionCode;
    private String bdChannelId;
    private String bdTokenId;
    private String platformType;
    private Integer mobileOS;

    public LoginRequest() {
    }

    public LoginRequest(String userId, String userType, String password, String imei, Integer appVersionCode, String bdChannelId, String bdTokenId, String platformType, Integer mobileOS) {
        this.userId = userId;
        this.userType = userType;
        this.password = password;
        this.imei = imei;
        this.appVersionCode = appVersionCode;
        this.bdChannelId = bdChannelId;
        this.bdTokenId = bdTokenId;
        this.platformType = platformType;
        this.mobileOS = mobileOS;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public Integer getAppVersionCode() {
        return appVersionCode;
    }

    public void setAppVersionCode(Integer appVersionCode) {
        this.appVersionCode = appVersionCode;
    }

    public String getBdChannelId() {
        return bdChannelId;
    }

    public void setBdChannelId(String bdChannelId) {
        this.bdChannelId = bdChannelId;
    }

    public String getBdTokenId() {
        return bdTokenId;
    }

    public void setBdTokenId(String bdTokenId) {
        this.bdTokenId = bdTokenId;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public Integer getMobileOS() {
        // 默认安卓
        if (mobileOS == null) {
            mobileOS = 0;
        }
        return mobileOS;
    }

    public void setMobileOS(Integer mobileOS) {
        this.mobileOS = mobileOS;
    }

    @Override
    public String toString() {
        return "LoginRequest{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", password='" + password + '\'' +
                ", imei='" + imei + '\'' +
                ", appVersionCode=" + appVersionCode +
                ", bdChannelId='" + bdChannelId + '\'' +
                ", bdTokenId='" + bdTokenId + '\'' +
                ", platformType='" + platformType + '\'' +
                ", mobileOS=" + mobileOS +
                '}';
    }
}
