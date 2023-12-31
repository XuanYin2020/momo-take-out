package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * 配置属性类: 封装配置文件中的一些配置项, 封装成了一个java对象
 *
 */

@Component
@ConfigurationProperties(prefix = "sky.jwt") //配置属性类，application.yml下面的相关内容sky.jwt，里面包含具体配置项
@Data
public class JwtProperties {

    /**
     * 管理端员工生成jwt令牌相关配置
     */
    private String adminSecretKey;
    private long adminTtl;
    private String adminTokenName;

    /**
     * 用户端微信用户生成jwt令牌相关配置
     */
    private String userSecretKey;
    private long userTtl;
    private String userTokenName;

}
