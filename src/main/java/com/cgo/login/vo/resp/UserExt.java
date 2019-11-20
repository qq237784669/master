package com.cgo.login.vo.resp;
import com.cgo.login.dto.AppMenuAuth;
import com.cgo.login.dto.VehicleIcon;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;
import java.util.List;


/**
 * 扩展自定义用户类
 */
@Data
public  class UserExt extends User {
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

    public UserExt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
