package com.github.catalpaflat.third.support;

import com.github.catalpaflat.third.config.ThirdPartyLoginYmlConfig;
import com.github.catalpaflat.third.exception.ThirdLoginException;
import com.github.catalpaflat.third.model.to.AppIdWithSecretTO;
import com.github.catalpaflat.third.model.to.ThirdLoginQQTO;
import com.github.catalpaflat.third.model.to.ThirdLoginTO;
import com.github.catalpaflat.third.model.to.ThirdLoginWxTO;

/**
 * @author CatalpaFlat
 */
public class ThirdLoginSupport {
    private ThirdPartyLoginYmlConfig thirdPartyLoginYmlConfig;

    protected static ThirdLoginTO thirdLoginTO;
    protected static ThirdLoginWxTO thirdLoginWxTO;
    protected static ThirdLoginQQTO thirdLoginQQTO;
    protected static AppIdWithSecretTO wxAppAppIdWithSecretTO;
    protected static AppIdWithSecretTO wxMiniAppIdWithSecretTO;
    protected static AppIdWithSecretTO wxH5AppIdWithSecretTO;
    protected static AppIdWithSecretTO qqH5AppIdWithSecretTO;

    public ThirdLoginSupport(ThirdPartyLoginYmlConfig thirdPartyLoginYmlConfig) {
        this.thirdPartyLoginYmlConfig = thirdPartyLoginYmlConfig;
        initThirdLogin();
    }

    private void initThirdLogin() {
        if (thirdPartyLoginYmlConfig == null) {
            throw new ThirdLoginException("ThirdPartyLoginYmlConfig is empty");
        }
        ThirdLoginTO thirdLogin = thirdPartyLoginYmlConfig.getThirdLogin();
        if (thirdLogin == null) {
            throw new ThirdLoginException("thirdLogin is empty");
        }
        thirdLoginTO = thirdLogin;
    }

    public ThirdLoginWxTO initThirdLoginWx() {
        ThirdLoginWxTO wx = thirdLoginTO.getWx();
        if (wx == null) {
            throw new ThirdLoginException("wx is empty");
        }
        thirdLoginWxTO = wx;
        return wx;
    }

    public ThirdLoginQQTO initThirdLoginQQ() {
        ThirdLoginQQTO qq = thirdLoginTO.getQq();
        if (qq == null) {
            throw new ThirdLoginException("wx is empty");
        }
        thirdLoginQQTO = qq;
        return qq;
    }

    public AppIdWithSecretTO initWxAppAppIdWithSecret() {
        AppIdWithSecretTO app = thirdLoginWxTO.getApp();
        if (app == null) {
            throw new ThirdLoginException("wx app appId or Secret is empty");
        }
        wxAppAppIdWithSecretTO = app;
        return app;
    }

    public AppIdWithSecretTO initWxMiniAppIdWithSecret() {
        AppIdWithSecretTO app = thirdLoginWxTO.getMini();
        if (app == null) {
            throw new ThirdLoginException("wx mini appId or Secret is empty");
        }
        wxMiniAppIdWithSecretTO = app;
        return app;
    }

    public AppIdWithSecretTO initWxH5AppIdWithSecret() {
        AppIdWithSecretTO app = thirdLoginWxTO.getH5();
        if (app == null) {
            throw new ThirdLoginException("wx h5 appId or Secret is empty");
        }
        wxH5AppIdWithSecretTO = app;
        return app;
    }

    public AppIdWithSecretTO initQQH5AppIdWithSecret() {
        AppIdWithSecretTO app = thirdLoginQQTO.getH5();
        if (app == null) {
            throw new ThirdLoginException("qq h5 appId or Secret is empty");
        }
        qqH5AppIdWithSecretTO = app;
        return app;
    }
}
