package com.cgo.login.config;

import java.util.ArrayList;

/**
 * Author: ChenYiQiang
 * Created: 2019-11-06 20:09
 * Description: 用户自定义配置
 */
public class CustomConfig {
    private boolean hasDeviceData;//是否有外设数据表
    private ArrayList<String> CusModule = new ArrayList<>();

    public CustomConfig() {
    }

    public CustomConfig(boolean hasDeviceData, ArrayList<String> cusModule) {
        this.hasDeviceData = hasDeviceData;
        CusModule = cusModule;
    }

    public boolean isHasDeviceData() {
        return hasDeviceData;
    }

    public void setHasDeviceData(boolean hasDeviceData) {
        this.hasDeviceData = hasDeviceData;
    }

    public ArrayList<String> getCusModule() {
        return CusModule;
    }

    public void setCusModule(ArrayList<String> cusModule) {
        CusModule = cusModule;
    }

    @Override
    public String toString() {
        return "CustomConfig{" +
                "hasDeviceData=" + hasDeviceData +
                ", CusModule=" + CusModule +
                '}';
    }
}
