package com.github.catalpaflat.third.support;

import com.github.catalpaflat.third.config.ThirdPartyLoginYmlConfig;
import com.github.catalpaflat.third.constant.dynamic.ThirdLoginConstant;
import com.github.catalpaflat.third.exception.ThirdLoginException;
import com.github.catalpaflat.third.support.http.HttpClientSupport;
import com.github.catalpaflat.third.utils.AESEncrypt;
import com.google.gson.Gson;
import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import java.io.UnsupportedEncodingException;

import static com.github.catalpaflat.third.constant.ThirdLoginConstant.*;


/**
 * @author CatalpaFlat
 */
public class ThirdLoginWxMiniSupport extends ThirdLoginSupport {

    public ThirdLoginWxMiniSupport(ThirdPartyLoginYmlConfig thirdPartyLoginYmlConfig) {
        super(thirdPartyLoginYmlConfig);
        initThirdLoginWx();
        initWxMiniAppIdWithSecret();
    }

    public  JSONObject obtainOpenId(String jsCode) throws Exception {
        String appId = wxMiniAppIdWithSecretTO.getAppId();
        if (StringUtils.isBlank(appId)) {
            throw new ThirdLoginException("wx app appId is empty");
        }
        String secret = wxMiniAppIdWithSecretTO.getSecret();
        if (StringUtils.isBlank(secret)) {
            throw new ThirdLoginException("wx app secret is empty");
        }
        String wxMiniUserInfoUrl = ThirdLoginConstant.WX_MINI_USER_INFO_URL;
        if (StringUtils.isBlank(wxMiniUserInfoUrl)) {
            throw new ThirdLoginException("wx mini url is blank");
        }
        String url = wxMiniUserInfoUrl +
                URL_SRTRING_KEY1 +
                URL_KEY_APP_ID +
                URL_SRTRING_KEY3 +
                appId +
                URL_SRTRING_KEY2 +
                URL_KEY_SECRET +
                URL_SRTRING_KEY3 +
                secret +
                URL_SRTRING_KEY2 +
                URL_KEY_JS_CODE +
                URL_SRTRING_KEY3 +
                jsCode +
                URL_SRTRING_KEY2 +
                URL_KEY_GRANT_TYPE +
                URL_SRTRING_KEY3 +
                URL_KEY_AUTHORIZATION_CODE;

        String result = HttpClientSupport.get(url, "UTF-8");
        if (StringUtils.isBlank(result)) {
            throw new ThirdLoginException("wx mini obtain access_token result is empty");
        }
        JSONObject responseJson = JSONObject.fromObject(result);
        if (responseJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(responseJson));
        }

        return responseJson;
    }

    public  JSONObject obtainUserInfo(String sessionKey, String encryptedData, String iv) throws UnsupportedEncodingException {

        byte[] resultByte = AESEncrypt.decrypt(Base64.decode(encryptedData), Base64.decode(sessionKey), Base64.decode(iv));
        if (null != resultByte && resultByte.length > 0) {
            String userInfo = new String(resultByte, "UTF-8");
            if (StringUtils.isBlank(userInfo)) {
                throw new ThirdLoginException("user info is blank");
            }
            JSONObject infoJson = JSONObject.fromObject(userInfo);
            if (infoJson.containsKey(ERRCODE)) {
                throw new ThirdLoginException(new Gson().toJson(infoJson.getJSONObject(ERRCODE)));
            }
            return infoJson;
        }
        return null;
    }

    public  JSONObject obtainUserInfoByJsCode(String jsCode, String encryptedData, String iv) throws Exception {
        JSONObject openIdJson = obtainOpenId(jsCode);

        if (!openIdJson.containsKey(SESSION_KEY)) {
            throw new ThirdLoginException("session key is blank");
        }
        String sessionKey = openIdJson.getString(SESSION_KEY);
        return obtainUserInfo(sessionKey, encryptedData, iv);
    }
}
