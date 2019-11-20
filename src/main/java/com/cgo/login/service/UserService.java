package com.cgo.login.service;


import com.cgo.login.config.CustomConfig;
import com.cgo.login.config.GlobalConfig;
import com.cgo.login.dto.AppMenuAuth;
import com.cgo.login.dto.VehicleIcon;
import com.cgo.login.utlis.EncryptionUtil;
import com.cgo.login.utlis.JDBCUtil;
import com.cgo.login.vo.req.LoginRequest;
import com.cgo.login.vo.resp.LoginResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.cgo.login.dto.APICallback;


@Slf4j
@Service
public class UserService {
    @Autowired
    private GlobalConfig globalConfig;

    @Autowired
    private JDBCUtil jdbcUtil;

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public APICallback login(@RequestBody LoginRequest loginRequest) throws SQLException {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet loginUserInfo = null;
        ResultSet userAuthRS = null;

        //region 测试
        log.info("/*-------------------------------");
        log.info("测试信息开始：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))  + "------------");
        log.info("账户登录输入参数：");
        log.info(loginRequest.toString());
        log.info("测试结束：" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(System.currentTimeMillis()))  + "------------");
        log.info("-------------------------------*/");
        //endregion

        try {
            String userId = loginRequest.getUserId();
            String password = loginRequest.getPassword();
            String userType = loginRequest.getUserType();
            Integer mobileOs = loginRequest.getMobileOS();
            String platformType = loginRequest.getPlatformType();
            String bdTokenId = loginRequest.getBdTokenId();
            String bdChannelId = loginRequest.getBdChannelId();
            String imei = loginRequest.getImei();

            LoginResponse loginResponse = null;
            APICallback apiCallback = null; // 返回参数

            if (platformType != null && !platformType.isEmpty() && platformType.equals("top6")) {
                apiCallback = buildAPICallback(false, -10, "该平台请使用TOP6产品线App，点击确认前往下载。", null);
                if (mobileOs != null && mobileOs == 0) {
                    apiCallback.setData(globalConfig.getAndroidTop6AppDownloadUrl());
                } else if (mobileOs != null && mobileOs == 1) {
                    apiCallback.setData(globalConfig.getIosTop6AppDownloadUrl());
                }
                return apiCallback;
            }

            if (mobileOs != null && mobileOs == 0 && loginRequest.getAppVersionCode() < globalConfig.getMinApkVersionCode()) {
                return buildAPICallback(false, -10, globalConfig.getMinApkVersionCodeErrDesc(), null);
            }

            if (userId == null || userId.isEmpty() || password == null || password.isEmpty()) {
                return buildAPICallback(false, -1, "请输入用户名/密码。", null);
            }

            /*
            （未翻译）
            日志处理
            判断云端授权情况
            */

            if (loginRequest.getBdTokenId() == null) {
                loginRequest.setBdTokenId("");
            }
            if (loginRequest.getBdChannelId() == null) {
                loginRequest.setBdChannelId("");
            }
            if (loginRequest.getImei() == null) {
                loginRequest.setImei("");
            }

            //region 获取登录用户信息
            conn = jdbcUtil.getConnection();
            pstmt = conn.prepareStatement(buildGetUserLoginInfoSqlText(userType));

            // 判断是用户登录还是车牌号登录
            if (userType.equals("0")) {
                pstmt.setString(1, userId);
            } else {
                pstmt.setString(1, userId);
                pstmt.setString(2, userId);
            }
            // 获取登录用户信息
            loginUserInfo = getLoginUserInfo(pstmt, userId, userType);
            //endregion

            boolean exists = loginUserInfo.next(); // 是否存在该用户
            if (exists && !userType.equals("0")) {
                return buildAPICallback(false,
                        -1,
                        "您输入的号牌【XXXX】存在不同颜色的多辆车,请在车牌号码后加颜色重新登录,如:测A12345黄。",
                        null);
            }

            if (!exists ||
                    !validPassword(loginRequest.getPassword(), loginUserInfo.getString("UserPwd"))) {
                return buildAPICallback(false,
                        -1,
                        "用户名/密码不正确。",
                        null);
            }

            ///新加车辆登录是否有权限，根据IsDenyWebGps判断 0表示无权限
            if (userType.equals("1") &&
                    loginUserInfo.getString("IsDenyWebGps").equals("0")) {
                return buildAPICallback(false,
                        -1,
                        "该车辆没有权限。",
                        null);
            }

            ///新加车辆登录判断是否到期自动冻结
            if (userType.equals("1") &&
                    loginUserInfo.getString("IsStay").equals("1")) {
                return buildAPICallback(false,
                        -1,
                        "登录失败,车辆已到期冻结。",
                        null);
            }

            boolean isUserAuth = false;
            //先判断有没有采用权限功能
            pstmt = conn.prepareStatement(buildGetUserAuthSqlText());
            pstmt.setString(1, userId);
            userAuthRS = getUserAuthRS(pstmt, userId);
            while (userAuthRS.next()) {
                if (!userAuthRS.getString("isExist").equals("0")) {
                    isUserAuth = true;
                    if (userAuthRS.getString("hasModule").equals("0") &&
                            userType.equals("0"))
                    //采用权限功能的话，判断用户是否采用手机模块
                    {
                        return buildAPICallback(false,
                                -1,
                                "用户没有登录权限。",
                                null);
                    }
                }
            }

            if (mobileOs != null &&
                    mobileOs != 3) {
//                CallableStatement cstmt = conn.prepareCall("{call dbo.spApp_ModifyMobOnLineUser(?, ?, ?, ?, ?, ?)}");
//                cstmt.setString(1, userId);
//                cstmt.setString(2, bdTokenId);
//                cstmt.setString(3, bdChannelId);
//                cstmt.setInt(4, mobileOs);
//                cstmt.setString(5, imei);
//                cstmt.setString(6, userType);
//                int rowsAffected = modifyMobOnLineUser(cstmt);
            }

            // 获取登录用户响应
            loginResponse = new LoginResponse();
            loginResponse.setUserId(userId);
            loginResponse.setUserType(userType);
            loginResponse.setUserName(loginUserInfo.getString("UserName"));
            loginResponse.setRoleName(loginUserInfo.getString("RoleName"));
            loginResponse.setOrgId(loginUserInfo.getString("OrgId"));
            loginResponse.setOrgName(loginUserInfo.getString("OrgName"));
            loginResponse.setAlarmType(loginUserInfo.getString("AlarmType"));
            loginResponse.setPlatformType("1");
            loginResponse.setSelectedCars(globalConfig.getSelectedCars());
            loginResponse.setHasVideoModule(globalConfig.getHasVideo());
            loginResponse.setHasVideoPush(globalConfig.getStartVideoAlarmPush());
            loginResponse.setServerVersion(globalConfig.getServerVersion());
            loginResponse.setAppMenuAuth(getAppMenuAuth(userId, userType, isUserAuth));
            loginResponse.setVehicleIconList(getVehicleIcons());

            if (userType.equals("1")) {
                loginResponse.setExpireDay("8");//车牌登录不判断过期默认8天
            } else {
                loginResponse.setExpireDay(loginUserInfo.getString("ExpiryDay"));
            }

            //构建用户登录请求响应报文
            apiCallback = new APICallback();
            apiCallback.setSuccess(true);
            apiCallback.setStatusCode(1);
            apiCallback.setMessage("");
            apiCallback.setData(loginResponse);

            return apiCallback;
        } catch (SQLException e) {
            e.printStackTrace();
            return buildAPICallback(false,
                    -1,
                    "用户登录失败，请稍后再试。",
                    null);
        } finally {
            jdbcUtil.close(loginUserInfo, pstmt, conn);
            jdbcUtil.close(userAuthRS, pstmt, conn);
        }
    }

    private List<VehicleIcon> getVehicleIcons() {
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        StringBuffer sqlTextSb = new StringBuffer();
        sqlTextSb.append("SELECT * ");
        sqlTextSb.append("FROM   sys_VehicleIcon ");
        sqlTextSb.append("WHERE  IconType IN ( 'car', 'default' ) ");
        sqlTextSb.append("ORDER  BY IconOrder ");

        ArrayList<VehicleIcon> vehicleIconList = new ArrayList<>();
        try {
            conn = jdbcUtil.getConnection();
            pstmt = conn.prepareStatement(sqlTextSb.toString());
            rs = pstmt.executeQuery();
            while (rs.next()) {
                VehicleIcon vi = new VehicleIcon();
                vi.setIconType(rs.getString("IconType"));
                vi.setIconName(rs.getString("IconName"));
                vi.setIconOrder(rs.getString("IconOrder"));
                vi.setOffline(rs.getString("offline"));
                vi.setDrive_normal(rs.getString("drive_normal"));
                vi.setDrive_alarm(rs.getString("drive_alarm"));
                vi.setDrive_full(rs.getString("drive_full"));
                vi.setDrive_operateoff(rs.getString("drive_operateoff"));
                vi.setSpeed0_normal(rs.getString("speed0_normal"));
                vi.setSpeed0_alarm(rs.getString("speed0_alarm"));
                vi.setSpeed0_full(rs.getString("speed0_full"));
                vi.setSpeed0_operateoff(rs.getString("speed0_operateoff"));
                vi.setAccoff_normal(rs.getString("accoff_normal"));
                vi.setAccoff_alarm(rs.getString("accoff_alarm"));
                vi.setAccoff_full(rs.getString("accoff_full"));
                vi.setAccoff_operateoff(rs.getString("accoff_operateoff"));
                vi.setGpsinvalid_normal(rs.getString("gpsinvalid_normal"));
                vi.setGpsinvalid_alarm(rs.getString("gpsinvalid_alarm"));
                vi.setGpsinvalid_full(rs.getString("gpsinvalid_full"));
                vi.setGpsinvalid_operateoff(rs.getString("gpsinvalid_operateoff"));
                vehicleIconList.add(vi);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            jdbcUtil.close(rs, pstmt, conn);
        }
        return vehicleIconList;
    }

    private AppMenuAuth getAppMenuAuth(String userId, String userType, boolean isUserAuth) {

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        AppMenuAuth appMenuAuth = new AppMenuAuth();
        appMenuAuth.setDeviceConfig(globalConfig.getDeviceConfig());
        CustomConfig customConfig = new CustomConfig();
        appMenuAuth.setDeviceConfig(customConfig.isHasDeviceData() ? globalConfig.getDeviceConfig() : "0");

        if (!isUserAuth) {
            appMenuAuth.setNavAuth(true);
            appMenuAuth.setTrackAuth(true);
            appMenuAuth.setTakePicAuth(true);
            appMenuAuth.setGetPicAuth(true);
            appMenuAuth.setMileAuth(true);
            appMenuAuth.setFollowAuth(true);
            appMenuAuth.setDetailAuth(true);
            appMenuAuth.setVideoAuth(true);
            appMenuAuth.setGetHistoryPicAuth(true);
            appMenuAuth.setSwitchCustomerAuth(true);
            appMenuAuth.setAlarmSetAuth(true);
            appMenuAuth.setPushSetAuth(true);
            appMenuAuth.setAdasPushSetAuth(false);
            appMenuAuth.setIsWorkAuth(false);
        } else {
            //用户登录
            if (userType.equals("0")) {
                try {
                    conn = jdbcUtil.getConnection();
                    pstmt = conn.prepareStatement("{call dbo.spApp_GetNavMenuAuthByUser(?)}");
                    pstmt.setString(1, userId);

                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        String navUserMenuAuth = rs.getString("navUserMenuAuth");
                        String navRoleMenuAuth = rs.getString("navRoleMenuAuth");
                        if (navUserMenuAuth.equals("R") || navRoleMenuAuth.equals("R")) {
                            appMenuAuth.setNavAuth(true);
                        } else {
                            appMenuAuth.setNavAuth(false);
                        }

                        String trackUserMenuAuth = rs.getString("trackUserMenuAuth");
                        String trackRoleMenuAuth = rs.getString("trackRoleMenuAuth");
                        if (trackUserMenuAuth.equals("R") || trackRoleMenuAuth.equals("R")) {
                            appMenuAuth.setTrackAuth(true);
                        } else {
                            appMenuAuth.setTrackAuth(false);
                        }

                        String takePicUserMenuAuth = rs.getString("takePicUserMenuAuth");
                        String takePicRoleMenuAuth = rs.getString("takePicRoleMenuAuth");
                        if (takePicUserMenuAuth.equals("RH") || takePicRoleMenuAuth.equals("RH")) {
                            appMenuAuth.setTakePicAuth(true);
                            appMenuAuth.setGetPicAuth(true);
                        } else if (takePicUserMenuAuth.equals("R") || takePicRoleMenuAuth.equals("R")) {
                            appMenuAuth.setTakePicAuth(true);
                            appMenuAuth.setGetPicAuth(false);
                        } else if (takePicUserMenuAuth.equals("H") || takePicRoleMenuAuth.equals("H")) {
                            appMenuAuth.setTakePicAuth(false);
                            appMenuAuth.setGetPicAuth(true);
                        } else {
                            appMenuAuth.setTakePicAuth(false);
                            appMenuAuth.setGetPicAuth(false);
                        }

                        String detailUserMenuAuth = rs.getString("detailUserMenuAuth");
                        String detailRoleMenuAuth = rs.getString("detailRoleMenuAuth");
                        if (detailUserMenuAuth.equals("R") || detailRoleMenuAuth.equals("R")) {
                            appMenuAuth.setDetailAuth(true);
                        } else {
                            appMenuAuth.setDetailAuth(false);
                        }

                        String mileUserMenuAuth = rs.getString("mileUserMenuAuth");
                        String mileRoleMenuAuth = rs.getString("mileRoleMenuAuth");
                        if (mileUserMenuAuth.equals("R") || mileRoleMenuAuth.equals("R")) {
                            appMenuAuth.setMileAuth(true);
                        } else {
                            appMenuAuth.setMileAuth(false);
                        }

                        String followUserMenuAuth = rs.getString("followUserMenuAuth");
                        String followRoleMenuAuth = rs.getString("followRoleMenuAuth");
                        if (followUserMenuAuth.equals("R") || followRoleMenuAuth.equals("R")) {
                            appMenuAuth.setFollowAuth(true);
                        } else {
                            appMenuAuth.setFollowAuth(false);
                        }

                        String videoUserMenuAuth = rs.getString("videoUserMenuAuth");
                        String videoRoleMenuAuth = rs.getString("videoRoleMenuAuth");
                        if (videoUserMenuAuth.equals("RH") || videoRoleMenuAuth.equals("RH")) {
                            appMenuAuth.setVideoAuth(true);
                            appMenuAuth.setGetHistoryPicAuth(true);
                        } else if (videoUserMenuAuth.equals("R") || videoRoleMenuAuth.equals("R")) {
                            appMenuAuth.setVideoAuth(true);
                            appMenuAuth.setGetHistoryPicAuth(false);
                        } else if (videoUserMenuAuth.equals("H") || videoRoleMenuAuth.equals("H")) {
                            appMenuAuth.setVideoAuth(false);
                            appMenuAuth.setGetHistoryPicAuth(true);
                        } else {
                            appMenuAuth.setVideoAuth(false);
                            appMenuAuth.setGetHistoryPicAuth(false);
                        }

                        String switchCustomerUserMenuAuth = rs.getString("switchCustomerUserMenuAuth");
                        String switchCustomerRoleMenuAuth = rs.getString("switchCustomerRoleMenuAuth");
                        if (switchCustomerUserMenuAuth.equals("R") || switchCustomerRoleMenuAuth.equals("R")) {
                            appMenuAuth.setSwitchCustomerAuth(true);
                        } else {
                            appMenuAuth.setSwitchCustomerAuth(false);
                        }

                        String alarmSetUserMenuAuth = rs.getString("alarmSetUserMenuAuth");
                        String alarmSetRoleMenuAuth = rs.getString("alarmSetRoleMenuAuth");
                        if (alarmSetUserMenuAuth.equals("R") || alarmSetRoleMenuAuth.equals("R")) {
                            appMenuAuth.setAlarmSetAuth(true);
                        } else {
                            appMenuAuth.setAlarmSetAuth(false);
                        }

                        String pushSetUserMenuAuth = rs.getString("pushSetUserMenuAuth");
                        String pushSetRoleMenuAuth = rs.getString("pushSetRoleMenuAuth");
                        if (pushSetUserMenuAuth.equals("R") || pushSetRoleMenuAuth.equals("R")) {
                            appMenuAuth.setPushSetAuth(true);
                        } else {
                            appMenuAuth.setPushSetAuth(false);
                        }

                        String adadPushSetUserMenuAuth = rs.getString("adasPushSetUserMenuAuth");
                        String adasPushSetRoleMenuAuth = rs.getString("adasPushSetRoleMenuAuth");
                        if (adadPushSetUserMenuAuth.equals("R") || adasPushSetRoleMenuAuth.equals("R")) {
                            appMenuAuth.setAdasPushSetAuth(true);
                        } else {
                            appMenuAuth.setAdasPushSetAuth(false);
                        }

                        String basicSurveySetUserMenuAuth = rs.getString("basicsurveySetUserMenuAuth");
                        String basicSurveySetRoleMenuAuth = rs.getString("basicsurveySetRoleMenuAuth");
                        if (basicSurveySetUserMenuAuth.equals("R") || basicSurveySetRoleMenuAuth.equals("R")) {
                            appMenuAuth.setBasicSurveryAuth(true);
                        } else {
                            appMenuAuth.setBasicSurveryAuth(false);
                        }
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    jdbcUtil.close(rs, pstmt, conn);
                }
            } else {
                // 车牌登录
                try {
                    conn = jdbcUtil.getConnection();
                    pstmt = conn.prepareStatement("{call dbo.spApp_GetNavMenuAuthByPlateNum(?)}");
                    pstmt.setString(1, userId);
                    int rowsCnt = 0;
                    rs = pstmt.executeQuery();
                    while (rs.next()) {
                        rowsCnt++;
                        if (rs.getString("isExit") == "0") {
                            appMenuAuth.setNavAuth(true);
                            appMenuAuth.setTrackAuth(true);
                            appMenuAuth.setTakePicAuth(false);
                            appMenuAuth.setGetPicAuth(false);
                            appMenuAuth.setMileAuth(true);
                            appMenuAuth.setFollowAuth(false);
                            appMenuAuth.setDetailAuth(true);
                            appMenuAuth.setVideoAuth(false);
                            appMenuAuth.setGetHistoryPicAuth(false);
                            appMenuAuth.setSwitchCustomerAuth(true);
                            appMenuAuth.setAlarmSetAuth(false);
                            appMenuAuth.setPushSetAuth(true);
                            appMenuAuth.setAdasPushSetAuth(false);
                            appMenuAuth.setBasicSurveryAuth(false);
                        } else {
                            if (rs.getString("NavAuth").equals("R")) {
                                appMenuAuth.setNavAuth(true);
                            } else {
                                appMenuAuth.setNavAuth(false);
                            }

                            if (rs.getString("GetTrackAuth").equals("R")) {
                                appMenuAuth.setTrackAuth(true);
                            } else {
                                appMenuAuth.setTrackAuth(false);
                            }

                            if (rs.getString("DetailAuth").equals("R")) {
                                appMenuAuth.setDetailAuth(true);
                            } else {
                                appMenuAuth.setDetailAuth(false);
                            }

                            if (rs.getString("MileAuth").equals("R")) {
                                appMenuAuth.setMileAuth(true);
                            } else {
                                appMenuAuth.setMileAuth(false);
                            }

                            if (rs.getString("FollowAuth").equals("R")) {
                                appMenuAuth.setFollowAuth(true);
                            } else {
                                appMenuAuth.setFollowAuth(false);
                            }

                            if (rs.getString("VideoAuth").equals("RH")) {
                                appMenuAuth.setVideoAuth(true);
                                appMenuAuth.setGetHistoryPicAuth(true);

                            } else if (rs.getString("VideoAuth").equals("R")) {
                                appMenuAuth.setVideoAuth(true);
                                appMenuAuth.setGetHistoryPicAuth(false);
                            } else if (rs.getString("VideoAuth").equals("H")) {
                                appMenuAuth.setVideoAuth(false);
                                appMenuAuth.setGetHistoryPicAuth(true);
                            } else {
                                appMenuAuth.setVideoAuth(false);
                                appMenuAuth.setGetHistoryPicAuth(false);
                            }

                            if (rs.getString("SwitchCusAuth").equals("R")) {
                                appMenuAuth.setSwitchCustomerAuth(true);
                            } else {
                                appMenuAuth.setSwitchCustomerAuth(false);
                            }

                            if (rs.getString("AlarmSetAuth").equals("R")) {
                                appMenuAuth.setAlarmSetAuth(true);
                            } else {
                                appMenuAuth.setAlarmSetAuth(false);
                            }

                            if (rs.getString("PushSetAuth").equals("R")) {
                                appMenuAuth.setPushSetAuth(true);
                            } else {
                                appMenuAuth.setPushSetAuth(false);
                            }

                            if (rs.getString("TakePicAuth").equals("RH")) {
                                appMenuAuth.setTakePicAuth(true);
                                appMenuAuth.setGetPicAuth(true);
                            } else if (rs.getString("TakePicAuth").equals("R")) {
                                appMenuAuth.setTakePicAuth(true);
                                appMenuAuth.setGetPicAuth(false);
                            } else if (rs.getString("TakePicAuth").equals("H")) {
                                appMenuAuth.setTakePicAuth(false);
                                appMenuAuth.setGetPicAuth(true);
                            } else {
                                appMenuAuth.setTakePicAuth(false);
                                appMenuAuth.setGetPicAuth(false);
                            }

                            appMenuAuth.setAdasPushSetAuth(false);
                            if (rs.getString("BasicsurveyAuth").equals("R")) {
                                appMenuAuth.setBasicSurveryAuth(true);
                            } else {
                                appMenuAuth.setBasicSurveryAuth(false);
                            }
                        }
                    }

                    if (rowsCnt == 0) {
                        appMenuAuth.setNavAuth(true);
                        appMenuAuth.setTrackAuth(true);
                        appMenuAuth.setTakePicAuth(false);
                        appMenuAuth.setGetPicAuth(false);
                        appMenuAuth.setMileAuth(true);
                        appMenuAuth.setFollowAuth(false);
                        appMenuAuth.setDetailAuth(true);
                        appMenuAuth.setVideoAuth(false);
                        appMenuAuth.setGetHistoryPicAuth(false);
                        appMenuAuth.setSwitchCustomerAuth(true);
                        appMenuAuth.setAlarmSetAuth(false);
                        appMenuAuth.setPushSetAuth(true);
                        appMenuAuth.setAdasPushSetAuth(false);
                        appMenuAuth.setBasicSurveryAuth(false);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    jdbcUtil.close(rs, pstmt, conn);
                }
            }
        }

        if (!customConfig.getCusModule().contains("TopActiveSafety")) {
            appMenuAuth.setAdasPushSetAuth(false);
        }

        // 单独判断是否有查岗功能
        if (globalConfig.getIsCheckWork().equals("1") &&
                userType.equals("0")) {
            try {
                conn = jdbcUtil.getConnection();
                pstmt = conn.prepareStatement("{call dbo.spApp_IsWorkMenuAuth(?)}");
                pstmt.setString(1, userId);
                rs = pstmt.executeQuery();
                if (rs.next()) {
                    if (rs.getString("isWorkMenuAuth").equals("R") ||
                            rs.getString("isWorkRoleMenuAuth").equals("R")) {
                        appMenuAuth.setIsWorkAuth(true);
                    } else {
                        appMenuAuth.setIsWorkAuth(false);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                jdbcUtil.close(rs, pstmt, conn);
            }
        } else {
            appMenuAuth.setIsWorkAuth(false);
        }

        return appMenuAuth;
    }

    private String buildGetUserLoginInfoSqlText(String userType) {
        StringBuffer sqlTextSb = new StringBuffer();

        // 判断是用户登录还是车牌号登录
        if (userType.equals("0")) {
            sqlTextSb.append("SELECT u.UserId, ");
            sqlTextSb.append("       u.UserPwd, ");
            sqlTextSb.append("       u.UserName, ");
            sqlTextSb.append("       g.RoleName, ");
            sqlTextSb.append("       o.OrgId, ");
            sqlTextSb.append("       o.OrgName, ");
            sqlTextSb.append("       Isnull(B.AlarmType, -1) AS AlarmType, ");
            sqlTextSb.append("       ExpiryDay=( CASE ");
            sqlTextSb.append("                     WHEN u.ExpiryDate IS NULL ");
            sqlTextSb.append("                           OR u.ExpiryDate = '' THEN 8 ");
            sqlTextSb.append("                     ELSE ( CASE ");
            sqlTextSb.append("                              WHEN Datediff(MINUTE, Getdate(), u.ExpiryDate) >= 0 THEN Datediff(day, Getdate(), u.ExpiryDate) ");
            sqlTextSb.append("                              ELSE -1 ");
            sqlTextSb.append("                            END ) ");
            sqlTextSb.append("                   END ), ");
            sqlTextSb.append("       IsStay=0 ");
            sqlTextSb.append("FROM   sys_User u ");
            sqlTextSb.append("       INNER JOIN sys_Role g ");
            sqlTextSb.append("               ON u.RoleId = g.RoleId ");
            sqlTextSb.append("       LEFT JOIN bas_Org o ");
            sqlTextSb.append("              ON u.OrgId = o.OrgId ");
            sqlTextSb.append("       LEFT JOIN biz_MobUserConfig B ");
            sqlTextSb.append("              ON u.UserId = B.UserId ");
            sqlTextSb.append("WHERE  u.UserId = ? ");
        } else {
            sqlTextSb.append("SELECT v.PlateNum   AS UserId, ");
            sqlTextSb.append("       v.[PassWord] AS UserPwd, ");
            sqlTextSb.append("       v.OwnerName  AS UserName, ");
            sqlTextSb.append("       v.CorpName   AS RoleName, ");
            sqlTextSb.append("       o.OrgId, ");
            sqlTextSb.append("       o.OrgName, ");
            sqlTextSb.append("       -1           AS AlarmType, ");
            sqlTextSb.append("       IsDenyWebGps=( CASE ");
            sqlTextSb.append("                        WHEN v.IsDenyWebGps = 1 THEN 1 ");
            sqlTextSb.append("                        ELSE 0 ");
            sqlTextSb.append("                      END ), ");
            sqlTextSb.append("       IsStay=( CASE ");
            sqlTextSb.append("                  WHEN v.IsAutoStay = 1 ");
            sqlTextSb.append("                       AND v.SVREndTime < CONVERT(VARCHAR(10), Getdate(), 120) THEN 1 ");
            sqlTextSb.append("                  ELSE 0 ");
            sqlTextSb.append("                END ) ");
            sqlTextSb.append("FROM   bas_Vehicle v ");
            sqlTextSb.append("       INNER JOIN bas_Org o ");
            sqlTextSb.append("               ON v.OrgId = o.OrgId ");
            sqlTextSb.append("       LEFT JOIN std_PlateColor c ");
            sqlTextSb.append("              ON v.ColorCode = c.ColorCode ");
            sqlTextSb.append("WHERE  v.PlateNum = ? ");
            sqlTextSb.append("        OR v.PlateNum + ( CASE ");
            sqlTextSb.append("                            WHEN c.ColorCode <> 9 THEN LEFT(c.ColorName, 1) ");
            sqlTextSb.append("                            ELSE c.ColorName ");
            sqlTextSb.append("                          END ) = ? ");
        }

        return sqlTextSb.toString();
    }

    private String buildGetUserAuthSqlText() {
        StringBuffer sqlTextSb = new StringBuffer();
        sqlTextSb.append("SELECT isExist=( Isnull((SELECT MaxAuth ");
        sqlTextSb.append("                         FROM   sys_Menu ");
        sqlTextSb.append("                         WHERE  MenuCode = 'GetTrackAuth'), '0') ), ");
        sqlTextSb.append("       hasModule=( Isnull((SELECT ModuleCode ");
        sqlTextSb.append("                           FROM   sys_UserAuth_Module ");
        sqlTextSb.append("                           WHERE  UserId = ? ");
        sqlTextSb.append("                                  AND ModuleCode = 'TopMobile'), '0') ) ");
        return sqlTextSb.toString();
    }



    private int modifyMobOnLineUser(CallableStatement cstmt) {
        int rowsAffected = 0;
        try {
            cstmt.execute();
            rowsAffected = cstmt.getUpdateCount();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rowsAffected;
    }

    /**
     * 构建用户登录请求响应报文
     *
     * @param callbackSuccess    返回是否登录成功
     * @param callbackStatusCode 返回状态码
     * @param callbackMessage    返回消息
     * @param callbackData       返回数据
     * @return 用户登录请求响应报文
     */
    private APICallback buildAPICallback(boolean callbackSuccess, int callbackStatusCode,
                                         String callbackMessage, Object callbackData) {
        APICallback apiCallback = new APICallback();
        apiCallback.setSuccess(callbackSuccess);
        apiCallback.setStatusCode(callbackStatusCode);
        apiCallback.setMessage(callbackMessage);
        apiCallback.setData(callbackData);
        return apiCallback;
    }

    private ResultSet getLoginUserInfo(PreparedStatement pstmt, String userId, String userType) {
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    private ResultSet getUserAuthRS(PreparedStatement pstmt, String userId) {
        //连接数据库判断是否登录成功
        ResultSet rs = null;
        try {
            rs = pstmt.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return rs;
    }

    /**
     * 验证用户输入的密码
     *
     * @param inputPwd
     * @param dbPwd
     * @return
     */
    private boolean validPassword(String inputPwd, String dbPwd) {
        return inputPwd.equals(dbPwd)
                || EncryptionUtil.encrypByMD5(inputPwd).equals(dbPwd);
    }
}
