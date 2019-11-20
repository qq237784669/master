package com.cgo.login.service;

import com.alibaba.fastjson.JSON;
import com.cgo.login.vo.req.LoginRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.DefaultResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Map;

@Service
public class LoginService {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${server.port}")
    private String port;

    public String login(LoginRequest loginRequest) {
        String userId = loginRequest.getUserId();
        String password = loginRequest.getPassword();


        //配置请求头
        LinkedMultiValueMap<String, String> heard = new LinkedMultiValueMap<>();
        heard.add("Authorization",getHttpBasic("cgoclient","cgopwd"));

        // 请求体
        LinkedMultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type","password");
        body.add("username",userId);
        body.add("password",password);
        body.add("redirect_uri","http://localhost");
        body.add("loginRequest", JSON.toJSONString(loginRequest));

        // 配置  响应状态码为400  401 的时候 不抛出异常   因为权限不足不算 认证授权失败
        restTemplate.setErrorHandler(new DefaultResponseErrorHandler(){
            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                if(response.getRawStatusCode()!=400 && response.getRawStatusCode()!=401){
                    super.handleError(response);
                }
            }
        });

        //发起请求
        HttpEntity<LinkedMultiValueMap> requestParam = new HttpEntity<>(body,heard);
        //会回调UserDeatilService 中的方法
        ResponseEntity<Map> respones = restTemplate.exchange("http://127.0.0.1:{port}/oauth/token", HttpMethod.POST, requestParam, Map.class,port);
        Map responesParam = respones.getBody();

        //获取响应的 token
        String access_token = (String) responesParam.get("access_token");

        return access_token;
    }
    private String getHttpBasic(String clientId,String clientSecret){
        String string = clientId+":"+clientSecret;
        //将串进行base64编码
        byte[] encode = Base64Utils.encode(string.getBytes());
        return "Basic "+new String(encode);
    }
}
