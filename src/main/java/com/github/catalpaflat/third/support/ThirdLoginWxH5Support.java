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
public class ThirdLoginWxH5Support extends ThirdLoginSupport {

    public ThirdLoginWxH5Support(ThirdPartyLoginYmlConfig thirdPartyLoginYmlConfig) {
        super(thirdPartyLoginYmlConfig);
        initThirdLoginWx();
        initWxH5AppIdWithSecret();
    }

    public JSONObject obtainAccessToken(String code) throws Exception {
        String appId = wxH5AppIdWithSecretTO.getAppId();
        if (StringUtils.isBlank(appId)) {
            throw new ThirdLoginException("wx app appId is empty");
        }
        String secret = wxH5AppIdWithSecretTO.getSecret();
        if (StringUtils.isBlank(secret)) {
            throw new ThirdLoginException("wx app secret is empty");
        }
        String wxH5AccessTokenUrl = ThirdLoginConstant.WX_H5_ACCESS_TOKEN_URL;
        if (StringUtils.isBlank(wxH5AccessTokenUrl)) {
            throw new ThirdLoginException("wx h5 url is blank");
        }
        String url = wxH5AccessTokenUrl +
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
            throw new ThirdLoginException("wx h5 obtain access_token result is empty");
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
        String wxH5UserInfoUrl = ThirdLoginConstant.WX_H5_USER_INFO_URL;
        if (StringUtils.isBlank(wxH5UserInfoUrl)) {
            throw new ThirdLoginException("wx h5 url is blank");
        }
        String url = wxH5UserInfoUrl +
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
        String wxH5UserInfoUrl = ThirdLoginConstant.WX_H5_USER_INFO_URL;
        if (StringUtils.isBlank(wxH5UserInfoUrl)) {
            throw new ThirdLoginException("wx h5 url is blank");
        }
        String accessToken = accessTokenInfoJson.getString(ACCESS_TOKEN);
        String openId = accessTokenInfoJson.getString(OPEN_ID);
        String url = wxH5UserInfoUrl +
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
            throw new ThirdLoginException("wx h5 obtain access_token result is empty");
        }
        JSONObject responseJson = JSONObject.fromObject(result);
        if (responseJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(responseJson.getJSONObject(ERRCODE)));
        }

        return responseJson;
    }
}
