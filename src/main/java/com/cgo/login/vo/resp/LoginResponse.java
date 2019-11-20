package com.cgo.login.vo.resp;

import com.cgo.login.dto.AppMenuAuth;
import com.cgo.login.dto.VehicleIcon;

import java.io.Serializable;
import java.util.List;


public class LoginResponse implements Serializable {
    private String userId;
    private String userType;
    private String userName;
    private String roleName;
    private String orgId;
    private String orgName;
    private String alarmType;
    private String top6AppDownloadUrl;
    private String platformType;
    private Integer selectedCars;
    private String hasVideoModule;
    private String hasVideoPush;
    private String serverVersion;
    private AppMenuAuth appMenuAuth;
    private String expireDay;
    private List<VehicleIcon> vehicleIconList;

    public LoginResponse() {
    }

    public LoginResponse(String userId, String userType, String userName, String roleName, String orgId, String orgName, String alarmType, String top6AppDownloadUrl, String platformType, Integer selectedCars, String hasVideoModule, String hasVideoPush, String serverVersion, AppMenuAuth appMenuAuth, String expireDay, List<VehicleIcon> vehicleIconList) {
        this.userId = userId;
        this.userType = userType;
        this.userName = userName;
        this.roleName = roleName;
        this.orgId = orgId;
        this.orgName = orgName;
        this.alarmType = alarmType;
        this.top6AppDownloadUrl = top6AppDownloadUrl;
        this.platformType = platformType;
        this.selectedCars = selectedCars;
        this.hasVideoModule = hasVideoModule;
        this.hasVideoPush = hasVideoPush;
        this.serverVersion = serverVersion;
        this.appMenuAuth = appMenuAuth;
        this.expireDay = expireDay;
        this.vehicleIconList = vehicleIconList;
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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    public String getOrgName() {
        return orgName;
    }

    public void setOrgName(String orgName) {
        this.orgName = orgName;
    }

    public String getAlarmType() {
        return alarmType;
    }

    public void setAlarmType(String alarmType) {
        this.alarmType = alarmType;
    }

    public String getTop6AppDownloadUrl() {
        return top6AppDownloadUrl;
    }

    public void setTop6AppDownloadUrl(String top6AppDownloadUrl) {
        this.top6AppDownloadUrl = top6AppDownloadUrl;
    }

    public String getPlatformType() {
        return platformType;
    }

    public void setPlatformType(String platformType) {
        this.platformType = platformType;
    }

    public Integer getSelectedCars() {
        return selectedCars;
    }

    public void setSelectedCars(Integer selectedCars) {
        this.selectedCars = selectedCars;
    }

    public String getHasVideoModule() {
        return hasVideoModule;
    }

    public void setHasVideoModule(String hasVideoModule) {
        this.hasVideoModule = hasVideoModule;
    }

    public String getHasVideoPush() {
        return hasVideoPush;
    }

    public void setHasVideoPush(String hasVideoPush) {
        this.hasVideoPush = hasVideoPush;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public AppMenuAuth getAppMenuAuth() {
        return appMenuAuth;
    }

    public void setAppMenuAuth(AppMenuAuth appMenuAuth) {
        this.appMenuAuth = appMenuAuth;
    }

    public String getExpireDay() {
        return expireDay;
    }

    public void setExpireDay(String expireDay) {
        this.expireDay = expireDay;
    }

    public List<VehicleIcon> getVehicleIconList() {
        return vehicleIconList;
    }

    public void setVehicleIconList(List<VehicleIcon> vehicleIconList) {
        this.vehicleIconList = vehicleIconList;
    }

    @Override
    public String toString() {
        return "LoginResponse{" +
                "userId='" + userId + '\'' +
                ", userType='" + userType + '\'' +
                ", userName='" + userName + '\'' +
                ", roleName='" + roleName + '\'' +
                ", orgId='" + orgId + '\'' +
                ", orgName='" + orgName + '\'' +
                ", alarmType='" + alarmType + '\'' +
                ", top6AppDownloadUrl='" + top6AppDownloadUrl + '\'' +
                ", platformType='" + platformType + '\'' +
                ", selectedCars=" + selectedCars +
                ", hasVideoModule='" + hasVideoModule + '\'' +
                ", hasVideoPush='" + hasVideoPush + '\'' +
                ", serverVersion='" + serverVersion + '\'' +
                ", appMenuAuth=" + appMenuAuth +
                ", expireDay='" + expireDay + '\'' +
                ", vehicleIconList=" + vehicleIconList +
                '}';
    }
}

