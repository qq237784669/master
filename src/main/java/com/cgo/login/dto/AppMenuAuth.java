package com.cgo.login.dto;

import java.io.Serializable;

/**
 * Author: ChenYiQiang
 * Created: 2019-11-06 17:28
 * Description:
 */
public class AppMenuAuth implements Serializable {
    /**
     * 导航
     */
    private boolean navAuth;
    /**
     * 轨迹
     */
    private boolean trackAuth;
    /**
     * 拍照
     */
    private boolean takePicAuth;
    /**
     * 获取照片
     */
    private boolean getPicAuth;
    /**
     * 详细
     */
    private boolean detailAuth;
    /**
     * 里程
     */
    private boolean mileAuth;
    /**
     * 追踪
     */
    private boolean followAuth;
    /**
     * 视频
     */
    private boolean videoAuth;
    /**
     * 查岗功能
     */
    private boolean isWorkAuth;
    /**
     * 个人中心-切换用户
     */
    private boolean switchCustomerAuth;
    /**
     * 个人中心-报警设置
     */
    private boolean alarmSetAuth;
    /**
     * 个人中心-推送设置
     */
    private boolean pushSetAuth;
    /**
     * 个人中心-推送设置-主动安全推送设置
     */
    private boolean adasPushSetAuth;
    /**
     * 外设数据配置
     */
    private String deviceConfig;
    /**
     * 基础概况
     */
    private boolean basicSurveryAuth;
    /**
     * 获取历史视频
     */
    private boolean getHistoryPicAuth;

    public AppMenuAuth() {
        setNavAuth(true);
        setTrackAuth(true);
        setTakePicAuth(false);
        setGetPicAuth(false);
        setMileAuth(true);
        setFollowAuth(false);
        setDetailAuth(true);
        setVideoAuth(false);
        setIsWorkAuth(false); //部分平台有需要
        setSwitchCustomerAuth(true);
        setAlarmSetAuth(false);
        setPushSetAuth(true);
        setAdasPushSetAuth(false); //这个特殊，默认不开启
        setBasicSurveryAuth(false); //默认关闭
        setGetHistoryPicAuth(false);
    }

    public AppMenuAuth(boolean navAuth, boolean trackAuth, boolean takePicAuth, boolean getPicAuth, boolean detailAuth, boolean mileAuth, boolean followAuth, boolean videoAuth, boolean isWorkAuth, boolean switchCustomerAuth, boolean alarmSetAuth, boolean pushSetAuth, boolean adasPushSetAuth, String deviceConfig, boolean basicSurveryAuth, boolean getHistoryPicAuth) {
        this.navAuth = navAuth;
        this.trackAuth = trackAuth;
        this.takePicAuth = takePicAuth;
        this.getPicAuth = getPicAuth;
        this.detailAuth = detailAuth;
        this.mileAuth = mileAuth;
        this.followAuth = followAuth;
        this.videoAuth = videoAuth;
        this.isWorkAuth = isWorkAuth;
        this.switchCustomerAuth = switchCustomerAuth;
        this.alarmSetAuth = alarmSetAuth;
        this.pushSetAuth = pushSetAuth;
        this.adasPushSetAuth = adasPushSetAuth;
        this.deviceConfig = deviceConfig;
        this.basicSurveryAuth = basicSurveryAuth;
        this.getHistoryPicAuth = getHistoryPicAuth;
    }

    public boolean isNavAuth() {
        return navAuth;
    }

    public void setNavAuth(boolean navAuth) {
        this.navAuth = navAuth;
    }

    public boolean isTrackAuth() {
        return trackAuth;
    }

    public void setTrackAuth(boolean trackAuth) {
        this.trackAuth = trackAuth;
    }

    public boolean isTakePicAuth() {
        return takePicAuth;
    }

    public void setTakePicAuth(boolean takePicAuth) {
        this.takePicAuth = takePicAuth;
    }

    public boolean isGetPicAuth() {
        return getPicAuth;
    }

    public void setGetPicAuth(boolean getPicAuth) {
        this.getPicAuth = getPicAuth;
    }

    public boolean isDetailAuth() {
        return detailAuth;
    }

    public void setDetailAuth(boolean detailAuth) {
        this.detailAuth = detailAuth;
    }

    public boolean isMileAuth() {
        return mileAuth;
    }

    public void setMileAuth(boolean mileAuth) {
        this.mileAuth = mileAuth;
    }

    public boolean isFollowAuth() {
        return followAuth;
    }

    public void setFollowAuth(boolean followAuth) {
        this.followAuth = followAuth;
    }

    public boolean isVideoAuth() {
        return videoAuth;
    }

    public void setVideoAuth(boolean videoAuth) {
        this.videoAuth = videoAuth;
    }

    public boolean isWorkAuth() {
        return isWorkAuth;
    }

    public void setIsWorkAuth(boolean workAuth) {
        this.isWorkAuth = workAuth;
    }

    public boolean isSwitchCustomerAuth() {
        return switchCustomerAuth;
    }

    public void setSwitchCustomerAuth(boolean switchCustomerAuth) {
        this.switchCustomerAuth = switchCustomerAuth;
    }

    public boolean isAlarmSetAuth() {
        return alarmSetAuth;
    }

    public void setAlarmSetAuth(boolean alarmSetAuth) {
        this.alarmSetAuth = alarmSetAuth;
    }

    public boolean isPushSetAuth() {
        return pushSetAuth;
    }

    public void setPushSetAuth(boolean pushSetAuth) {
        this.pushSetAuth = pushSetAuth;
    }

    public boolean isAdasPushSetAuth() {
        return adasPushSetAuth;
    }

    public void setAdasPushSetAuth(boolean adasPushSetAuth) {
        this.adasPushSetAuth = adasPushSetAuth;
    }

    public String getDeviceConfig() {
        return deviceConfig;
    }

    public void setDeviceConfig(String deviceConfig) {
        this.deviceConfig = deviceConfig;
    }

    public boolean isBasicSurveryAuth() {
        return basicSurveryAuth;
    }

    public void setBasicSurveryAuth(boolean basicSurveryAuth) {
        this.basicSurveryAuth = basicSurveryAuth;
    }

    public boolean isGetHistoryPicAuth() {
        return getHistoryPicAuth;
    }

    public void setGetHistoryPicAuth(boolean getHistoryPicAuth) {
        this.getHistoryPicAuth = getHistoryPicAuth;
    }

    @Override
    public String toString() {
        return "AppMenuAuth{" +
                "navAuth=" + navAuth +
                ", trackAuth=" + trackAuth +
                ", takePicAuth=" + takePicAuth +
                ", getPicAuth=" + getPicAuth +
                ", detailAuth=" + detailAuth +
                ", mileAuth=" + mileAuth +
                ", followAuth=" + followAuth +
                ", videoAuth=" + videoAuth +
                ", isWorkAuth=" + isWorkAuth +
                ", switchCustomerAuth=" + switchCustomerAuth +
                ", alarmSetAuth=" + alarmSetAuth +
                ", pushSetAuth=" + pushSetAuth +
                ", adasPushSetAuth=" + adasPushSetAuth +
                ", deviceConfig='" + deviceConfig + '\'' +
                ", basicSurveryAuth=" + basicSurveryAuth +
                ", getHistoryPicAuth=" + getHistoryPicAuth +
                '}';
    }
}
