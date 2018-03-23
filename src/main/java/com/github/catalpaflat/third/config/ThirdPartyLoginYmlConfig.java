package com.github.catalpaflat.third.config;


import com.github.catalpaflat.third.model.to.ThirdLoginTO;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * @author ： CatalpaFlat
 */
@ConfigurationProperties(prefix = "catalpaflat")
public class ThirdPartyLoginYmlConfig {

    @Getter
    @Setter
    private ThirdLoginTO thirdLogin;

    /*
     * 数据格式：
     * catalpaflat
     *  thirdLogin:
     *   wx：
     *       h5:
     *           appId:
     *           secret:
     *       app:
     *           appId:
     *           secret:
     *       mini:
     *           appId:
     *           secret:
     *   qq:
     *       h5:
     *           appId:
     *           secret:
     *
     * */

}
