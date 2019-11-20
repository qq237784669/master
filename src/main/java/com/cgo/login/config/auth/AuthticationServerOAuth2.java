package com.cgo.login.config.auth;


import com.cgo.login.config.KeyConfig;
import com.cgo.login.service.UserDeatilService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;


@Configuration
@EnableAuthorizationServer
public class AuthticationServerOAuth2 extends AuthorizationServerConfigurerAdapter {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDeatilService userDeatilService;


    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
               .passwordEncoder(new BCryptPasswordEncoder())
                .tokenKeyAccess("permitAll()")                //   访问获取token 任意访问 无需认证
                .checkTokenAccess("isAuthenticated()")                //如果需要验证toen  需要认证通过
                .allowFormAuthenticationForClients();                //允许from表单提交 就是 允许 把toen信息 通过表单 请求体发送过来

    }
    //  oauth2 认证模式中 重要的一个环节就是 客户端id  客户端密码
    //  这个方法代表 你自己决定 客户端id 密码细节 否则采用 默认配置
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients
                //内存 存储 客户端id  客户端密码信息
                .inMemory()
                // 客户端 id  密码
                .withClient("cgoclient").secret(new BCryptPasswordEncoder().encode("cgopwd"))
                .accessTokenValiditySeconds(60*60*24*7)// 过期时间
                //配置支持的认证类型  授权码认证   支持刷新token    密码认证
                .authorizedGrantTypes("password","authorization_code", "client_credentials", "refresh_token")
                // 允许回调的 url  授权码模式会有一个回调
                .redirectUris("http://localhost")
                .scopes("app");
    }

    //配置 jwt存储类  该类的2个属性 一个是 jwt转换的 类  一个是存储jwt的方式类

    /**
     * token 分2种 1种 token无状态 就是一个uuid 一种是有状态 例如jwt  无状态toen  服务器有状态  有状态token  服务器无状态
     *  如果使用 jwt 服务器就不需要存储  jwt 通过算法 来解析签名即可
     *  如果是无状态 的token 那么就需要 从  数据库中或者 redis缓存中 通过uuid获取对应的 用户信息
     *  因为我们用的是 jwt 作为token  那么token是有状态的 所以 就不用 内存 和  数据库 或者redis来存储token了
     *  oauth2 只是一宗协议  没有指定非得用jwt 作为 token 你也可以使用 uuid作为token 所以才有 这么多 tokenstore方式
     * @return
     */
    @Bean
    public TokenStore tokenStore(  ){
        return new JwtTokenStore(accessTokenConverter(null));
    }



    //accessTokenConvert 访问token转换器  其实就是 对访问的token转换
    //游览器能带着token 过来 那么 必定你 响应了token回去
    //对访问的token进行 转换 其实就是对 服务器响应回去的token 进行转换
    //之所以需要转换 为了 安全 相当于加了一个 签名
    //增加公钥  私钥 加密方式  非对称加密  对jwt 进行签名
    @Bean
    public JwtAccessTokenConverter accessTokenConverter(@Qualifier("keyConfig") KeyConfig keyConfig){
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setKeyPair(
                new KeyStoreKeyFactory(
                        new ClassPathResource(keyConfig.getKeyFileName()),keyConfig.getStorePwd().toCharArray()
                ).getKeyPair(keyConfig.getAlias(),keyConfig.getPwd().toCharArray())
        );
        //把自己定义的DefaultUserAuthenticationConverter
        //设置到 默认的访问token转换器中 这样可以覆盖 springSecurity自带的 DefaultUserAuthenticationConverter
        //因为自带的DefaultUserAuthenticationConverter  在进行 生成 jwt的时候 通过new一个map出来的 然后把map 转成json
        //然后用 私钥加密 ，实现 DefaultUserAuthenticationConverter  重写convert方法 然后增加 map中的用户信息
        //这样可以自定义一些字段
        DefaultAccessTokenConverter accessTokenConverter = (DefaultAccessTokenConverter) converter.getAccessTokenConverter();
        //将修改后的 respoenesConvert 类 覆盖 默认的 增加jwt Json字符串的内容

        accessTokenConverter.setUserTokenConverter(new UserJwtExpansion());
        return converter;
    }

    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {

        endpoints
                .authenticationManager(authenticationManager)
                //设置token存储
                .tokenStore(tokenStore())
                .accessTokenConverter(accessTokenConverter(null))
                //设置自定义 user认证类
               .userDetailsService(userDeatilService);
    }
}
