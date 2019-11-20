package com.cgo.login.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * Author: ChenYiQiang
 * Created: 2019-11-06 10:49
 * Description: 全局配置
 */
@Component
@ConfigurationProperties(prefix = "globalconfig")
public class GlobalConfig {
    /**
     * Android版top6产品线下载地址
     */
    private String androidTop6AppDownloadUrl;

    /**
     * iOS版top6产品线下载地址
     */
    private String iosTop6AppDownloadUrl;

    /**
     * 安卓允许登录使用的最低版本号配置，低于这个版本，则请到360应用商店下载新版本
     */
    private Integer minApkVersionCode;

    /**
     * 最低版本号错误描述
     */
    private String minApkVersionCodeErrDesc;

    /**
     * 设置勾选车辆的限制
     */
    private Integer selectedCars;

    /**
     * 是否有视频功能 0 没有
     */
    private String hasVideo;

    /**
     * 是否开启视频推送 默认false 不开启
     */
    private String startVideoAlarmPush;

    /**
     * 服务端版本号
     */
    private String serverVersion;

    /**
     * 外设数据获取 默认都不显示， 当前油量、温度数据 默认显示，配置值3，
     * 载重数据、电量数据、胎压数据 默认不显示，
     * 按位配置控制（1-当前油量 2-温度数据 4-载重数据 8-电量数据 16-胎压数据）
     * 解析的时候只有1路时，不显示传感器通道名称，直接显示值信息
     */
    private String deviceConfig;

    /**
     * 设置是否推送查岗信息 默认0不允许
     */
    private String isCheckWork;

    /**
     * 定时获取查岗定时器间隔 单位秒 默认900秒即15分钟
     */
    private String getCheckWorkInterval;

    /**
     * 查岗应答时效 单位分钟 默认15分钟
     */
    private String isWorkRspTime;

    public GlobalConfig() {
    }

    public GlobalConfig(String androidTop6AppDownloadUrl, String iosTop6AppDownloadUrl, Integer minApkVersionCode, String minApkVersionCodeErrDesc, Integer selectedCars, String hasVideo, String startVideoAlarmPush, String serverVersion, String deviceConfig, String isCheckWork, String getCheckWorkInterval, String isWorkRspTime) {
        this.androidTop6AppDownloadUrl = androidTop6AppDownloadUrl;
        this.iosTop6AppDownloadUrl = iosTop6AppDownloadUrl;
        this.minApkVersionCode = minApkVersionCode;
        this.minApkVersionCodeErrDesc = minApkVersionCodeErrDesc;
        this.selectedCars = selectedCars;
        this.hasVideo = hasVideo;
        this.startVideoAlarmPush = startVideoAlarmPush;
        this.serverVersion = serverVersion;
        this.deviceConfig = deviceConfig;
        this.isCheckWork = isCheckWork;
        this.getCheckWorkInterval = getCheckWorkInterval;
        this.isWorkRspTime = isWorkRspTime;
    }

    public String getAndroidTop6AppDownloadUrl() {
        return androidTop6AppDownloadUrl;
    }

    public void setAndroidTop6AppDownloadUrl(String androidTop6AppDownloadUrl) {
        this.androidTop6AppDownloadUrl = androidTop6AppDownloadUrl;
    }

    public String getIosTop6AppDownloadUrl() {
        return iosTop6AppDownloadUrl;
    }

    public void setIosTop6AppDownloadUrl(String iosTop6AppDownloadUrl) {
        this.iosTop6AppDownloadUrl = iosTop6AppDownloadUrl;
    }

    public Integer getMinApkVersionCode() {
        if (minApkVersionCode == null) {
            minApkVersionCode = 27;
        }
        return minApkVersionCode;
    }

    public void setMinApkVersionCode(Integer minApkVersionCode) {
        this.minApkVersionCode = minApkVersionCode;
    }

    public String getMinApkVersionCodeErrDesc() {
        if (minApkVersionCodeErrDesc == null) {
            minApkVersionCodeErrDesc = "当前软件版本较低，请到平台上扫描二维码下载新版本。";
        }
        return minApkVersionCodeErrDesc;
    }

    public void setMinApkVersionCodeErrDesc(String minApkVersionCodeErrDesc) {
        this.minApkVersionCodeErrDesc = minApkVersionCodeErrDesc;
    }

    public Integer getSelectedCars() {
        if (selectedCars == null) {
            selectedCars = 500;
        }
        return selectedCars;
    }

    public void setSelectedCars(Integer selectedCars) {
        this.selectedCars = selectedCars;
    }

    public String getHasVideo() {
        if (hasVideo == null) {
            hasVideo = "1";
        }
        return hasVideo;
    }

    public void setHasVideo(String hasVideo) {
        this.hasVideo = hasVideo;
    }

    public String getStartVideoAlarmPush() {
        if (androidTop6AppDownloadUrl == null) {
            startVideoAlarmPush = "false";
        }
        return startVideoAlarmPush;
    }

    public void setStartVideoAlarmPush(String startVideoAlarmPush) {
        this.startVideoAlarmPush = startVideoAlarmPush;
    }

    public String getServerVersion() {
        return serverVersion;
    }

    public void setServerVersion(String serverVersion) {
        this.serverVersion = serverVersion;
    }

    public String getDeviceConfig() {
        return deviceConfig;
    }

    public void setDeviceConfig(String deviceConfig) {
        this.deviceConfig = deviceConfig;
    }

    public String getIsCheckWork() {
        if (isCheckWork == null) {
            isCheckWork = "0";
        }
        return isCheckWork;
    }

    public void setIsCheckWork(String isCheckWork) {
        this.isCheckWork = isCheckWork;
    }

    public String getGetCheckWorkInterval() {
        return getCheckWorkInterval;
    }

    public void setGetCheckWorkInterval(String getCheckWorkInterval) {
        this.getCheckWorkInterval = getCheckWorkInterval;
    }

    public String getIsWorkRspTime() {
        return isWorkRspTime;
    }

    public void setIsWorkRspTime(String isWorkRspTime) {
        this.isWorkRspTime = isWorkRspTime;
    }

    @Override
    public String toString() {
        return "GlobalConfig{" +
                "androidTop6AppDownloadUrl='" + androidTop6AppDownloadUrl + '\'' +
                ", iosTop6AppDownloadUrl='" + iosTop6AppDownloadUrl + '\'' +
                ", minApkVersionCode=" + minApkVersionCode +
                ", minApkVersionCodeErrDesc='" + minApkVersionCodeErrDesc + '\'' +
                ", selectedCars=" + selectedCars +
                ", hasVideo='" + hasVideo + '\'' +
                ", startVideoAlarmPush='" + startVideoAlarmPush + '\'' +
                ", serverVersion='" + serverVersion + '\'' +
                ", deviceConfig='" + deviceConfig + '\'' +
                ", isCheckWork='" + isCheckWork + '\'' +
                ", getCheckWorkInterval='" + getCheckWorkInterval + '\'' +
                ", isWorkRspTime='" + isWorkRspTime + '\'' +
                '}';
    }
}
