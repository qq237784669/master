package com.cgo.login.dto;

import java.io.Serializable;

/**
 * Author: ChenYiQiang
 * Created: 2019-11-06 17:29
 * Description: 车辆图标
 */
public class VehicleIcon implements Serializable {
    private String iconType;
    private String iconName;
    private String iconOrder;
    private String offline;
    private String drive_normal;
    private String drive_alarm;
    private String drive_full;
    private String drive_operateoff;
    private String speed0_normal;
    private String speed0_alarm;
    private String speed0_full;
    private String speed0_operateoff;
    private String accoff_normal;
    private String accoff_alarm;
    private String accoff_full;
    private String accoff_operateoff;
    private String gpsinvalid_normal;
    private String gpsinvalid_alarm;
    private String gpsinvalid_full;
    private String gpsinvalid_operateoff;

    public VehicleIcon() {
    }

    public VehicleIcon(String iconType, String iconName, String iconOrder, String offline, String drive_normal, String drive_alarm, String drive_full, String drive_operateoff, String speed0_normal, String speed0_alarm, String speed0_full, String speed0_operateoff, String accoff_normal, String accoff_alarm, String accoff_full, String accoff_operateoff, String gpsinvalid_normal, String gpsinvalid_alarm, String gpsinvalid_full, String gpsinvalid_operateoff) {
        this.iconType = iconType;
        this.iconName = iconName;
        this.iconOrder = iconOrder;
        this.offline = offline;
        this.drive_normal = drive_normal;
        this.drive_alarm = drive_alarm;
        this.drive_full = drive_full;
        this.drive_operateoff = drive_operateoff;
        this.speed0_normal = speed0_normal;
        this.speed0_alarm = speed0_alarm;
        this.speed0_full = speed0_full;
        this.speed0_operateoff = speed0_operateoff;
        this.accoff_normal = accoff_normal;
        this.accoff_alarm = accoff_alarm;
        this.accoff_full = accoff_full;
        this.accoff_operateoff = accoff_operateoff;
        this.gpsinvalid_normal = gpsinvalid_normal;
        this.gpsinvalid_alarm = gpsinvalid_alarm;
        this.gpsinvalid_full = gpsinvalid_full;
        this.gpsinvalid_operateoff = gpsinvalid_operateoff;
    }

    public String getIconType() {
        return iconType;
    }

    public void setIconType(String iconType) {
        this.iconType = iconType;
    }

    public String getIconName() {
        return iconName;
    }

    public void setIconName(String iconName) {
        this.iconName = iconName;
    }

    public String getIconOrder() {
        return iconOrder;
    }

    public void setIconOrder(String iconOrder) {
        this.iconOrder = iconOrder;
    }

    public String getOffline() {
        return offline;
    }

    public void setOffline(String offline) {
        this.offline = offline;
    }

    public String getDrive_normal() {
        return drive_normal;
    }

    public void setDrive_normal(String drive_normal) {
        this.drive_normal = drive_normal;
    }

    public String getDrive_alarm() {
        return drive_alarm;
    }

    public void setDrive_alarm(String drive_alarm) {
        this.drive_alarm = drive_alarm;
    }

    public String getDrive_full() {
        return drive_full;
    }

    public void setDrive_full(String drive_full) {
        this.drive_full = drive_full;
    }

    public String getDrive_operateoff() {
        return drive_operateoff;
    }

    public void setDrive_operateoff(String drive_operateoff) {
        this.drive_operateoff = drive_operateoff;
    }

    public String getSpeed0_normal() {
        return speed0_normal;
    }

    public void setSpeed0_normal(String speed0_normal) {
        this.speed0_normal = speed0_normal;
    }

    public String getSpeed0_alarm() {
        return speed0_alarm;
    }

    public void setSpeed0_alarm(String speed0_alarm) {
        this.speed0_alarm = speed0_alarm;
    }

    public String getSpeed0_full() {
        return speed0_full;
    }

    public void setSpeed0_full(String speed0_full) {
        this.speed0_full = speed0_full;
    }

    public String getSpeed0_operateoff() {
        return speed0_operateoff;
    }

    public void setSpeed0_operateoff(String speed0_operateoff) {
        this.speed0_operateoff = speed0_operateoff;
    }

    public String getAccoff_normal() {
        return accoff_normal;
    }

    public void setAccoff_normal(String accoff_normal) {
        this.accoff_normal = accoff_normal;
    }

    public String getAccoff_alarm() {
        return accoff_alarm;
    }

    public void setAccoff_alarm(String accoff_alarm) {
        this.accoff_alarm = accoff_alarm;
    }

    public String getAccoff_full() {
        return accoff_full;
    }

    public void setAccoff_full(String accoff_full) {
        this.accoff_full = accoff_full;
    }

    public String getAccoff_operateoff() {
        return accoff_operateoff;
    }

    public void setAccoff_operateoff(String accoff_operateoff) {
        this.accoff_operateoff = accoff_operateoff;
    }

    public String getGpsinvalid_normal() {
        return gpsinvalid_normal;
    }

    public void setGpsinvalid_normal(String gpsinvalid_normal) {
        this.gpsinvalid_normal = gpsinvalid_normal;
    }

    public String getGpsinvalid_alarm() {
        return gpsinvalid_alarm;
    }

    public void setGpsinvalid_alarm(String gpsinvalid_alarm) {
        this.gpsinvalid_alarm = gpsinvalid_alarm;
    }

    public String getGpsinvalid_full() {
        return gpsinvalid_full;
    }

    public void setGpsinvalid_full(String gpsinvalid_full) {
        this.gpsinvalid_full = gpsinvalid_full;
    }

    public String getGpsinvalid_operateoff() {
        return gpsinvalid_operateoff;
    }

    public void setGpsinvalid_operateoff(String gpsinvalid_operateoff) {
        this.gpsinvalid_operateoff = gpsinvalid_operateoff;
    }

    @Override
    public String toString() {
        return "VehicleIcon{" +
                "iconType='" + iconType + '\'' +
                ", iconName='" + iconName + '\'' +
                ", iconOrder='" + iconOrder + '\'' +
                ", offline='" + offline + '\'' +
                ", drive_normal='" + drive_normal + '\'' +
                ", drive_alarm='" + drive_alarm + '\'' +
                ", drive_full='" + drive_full + '\'' +
                ", drive_operateoff='" + drive_operateoff + '\'' +
                ", speed0_normal='" + speed0_normal + '\'' +
                ", speed0_alarm='" + speed0_alarm + '\'' +
                ", speed0_full='" + speed0_full + '\'' +
                ", speed0_operateoff='" + speed0_operateoff + '\'' +
                ", accoff_normal='" + accoff_normal + '\'' +
                ", accoff_alarm='" + accoff_alarm + '\'' +
                ", accoff_full='" + accoff_full + '\'' +
                ", accoff_operateoff='" + accoff_operateoff + '\'' +
                ", gpsinvalid_normal='" + gpsinvalid_normal + '\'' +
                ", gpsinvalid_alarm='" + gpsinvalid_alarm + '\'' +
                ", gpsinvalid_full='" + gpsinvalid_full + '\'' +
                ", gpsinvalid_operateoff='" + gpsinvalid_operateoff + '\'' +
                '}';
    }
}
