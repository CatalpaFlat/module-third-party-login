package com.github.catalpaflat.third.support;

import com.github.catalpaflat.third.config.ThirdPartyLoginYmlConfig;
import com.github.catalpaflat.third.constant.dynamic.ThirdLoginConstant;
import com.github.catalpaflat.third.exception.ThirdLoginException;
import com.github.catalpaflat.third.support.http.HttpClientSupport;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import static com.github.catalpaflat.third.constant.ThirdLoginConstant.*;


/**
 * @author CatalpaFlat
 */
public class ThirdLoginWxAppSupport extends ThirdLoginSupport {

    public ThirdLoginWxAppSupport(ThirdPartyLoginYmlConfig thirdPartyLoginYmlConfig) {
        super(thirdPartyLoginYmlConfig);
        initThirdLoginWx();
        initWxAppAppIdWithSecret();
    }

    public JSONObject obtainAccessToken(String code) throws Exception {
        String appId = wxAppAppIdWithSecretTO.getAppId();
        if (StringUtils.isBlank(appId)) {
            throw new ThirdLoginException("wx app appId is empty");
        }
        String secret = wxAppAppIdWithSecretTO.getSecret();
        if (StringUtils.isBlank(secret)) {
            throw new ThirdLoginException("wx app secret is empty");
        }
        String wxAppAccessTokenUrl = ThirdLoginConstant.WX_APP_ACCESS_TOKEN_URL;
        if (StringUtils.isBlank(wxAppAccessTokenUrl)) {
            throw new ThirdLoginException("wx app url is blank");
        }
        String url = wxAppAccessTokenUrl +
                URL_SRTRING_KEY1 +
                URL_KEY_APP_ID +
                URL_SRTRING_KEY3 +
                appId +
                URL_SRTRING_KEY2 +
                URL_KEY_SECRET +
                URL_SRTRING_KEY3 +
                secret +
                URL_SRTRING_KEY2 +
                URL_KEY_CODE +
                URL_SRTRING_KEY3 +
                code +
                URL_SRTRING_KEY2 +
                URL_KEY_GRANT_TYPE +
                URL_SRTRING_KEY3 +
                URL_KEY_AUTHORIZATION_CODE;

        String result = HttpClientSupport.get(url, "UTF-8");
        if (StringUtils.isBlank(result)) {
            throw new ThirdLoginException("wx app obtain access_token result is empty");
        }
        JSONObject responseJson = JSONObject.fromObject(result);
        if (responseJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(responseJson.getJSONObject(ERRCODE)));
        }

        return responseJson;
    }

    public JSONObject obtainUserInfo(String accessToken, String openId) throws Exception {
        if (StringUtils.isBlank(accessToken) || StringUtils.isBlank(openId)) {
            throw new ThirdLoginException("accessToken or openId is blank");
        }
        String wxAppUserInfoUrl = ThirdLoginConstant.WX_APP_USER_INFO_URL;
        String url = wxAppUserInfoUrl +
                URL_SRTRING_KEY1 +
                ACCESS_TOKEN +
                URL_SRTRING_KEY3 +
                accessToken +
                URL_SRTRING_KEY2 +
                OPEN_ID +
                URL_SRTRING_KEY3 +
                openId;
        String result = HttpClientSupport.get(url, "UTF-8");
        if (StringUtils.isBlank(result)) {
            throw new ThirdLoginException("wx app obtain access_token result is empty");
        }
        JSONObject responseJson = JSONObject.fromObject(result);
        if (responseJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(responseJson.getJSONObject(ERRCODE)));
        }

        return responseJson;
    }


    public JSONObject obtainUserInfo(String code) throws Exception {
        JSONObject accessTokenInfoJson = obtainAccessToken(code);

        if (!accessTokenInfoJson.containsKey(ACCESS_TOKEN) || !accessTokenInfoJson.containsKey(OPEN_ID)) {
            throw new ThirdLoginException("WeChat app has no access token and openid in access token.");
        }
        String wxAppUserInfoUrl = ThirdLoginConstant.WX_APP_USER_INFO_URL;

        String accessToken = accessTokenInfoJson.getString(ACCESS_TOKEN);
        String openId = accessTokenInfoJson.getString(OPEN_ID);
        String url = wxAppUserInfoUrl +
                URL_SRTRING_KEY1 +
                ACCESS_TOKEN +
                URL_SRTRING_KEY3 +
                accessToken +
                URL_SRTRING_KEY2 +
                OPEN_ID +
                URL_SRTRING_KEY3 +
                openId;
        String result = HttpClientSupport.get(url, "UTF-8");
        if (StringUtils.isBlank(result)) {
            throw new ThirdLoginException("wx app obtain access_token result is empty");
        }
        JSONObject responseJson = JSONObject.fromObject(result);
        if (responseJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(responseJson.getJSONObject(ERRCODE)));
        }

        return responseJson;
    }
}
