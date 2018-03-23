package com.github.catalpaflat.third.support;

import com.github.catalpaflat.third.config.ThirdPartyLoginYmlConfig;
import com.github.catalpaflat.third.constant.dynamic.ThirdLoginConstant;
import com.github.catalpaflat.third.exception.ThirdLoginException;
import com.github.catalpaflat.third.support.http.HttpClientSupport;
import com.google.gson.Gson;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;

import static com.github.catalpaflat.third.constant.ThirdLoginConstant.ERRCODE;
import static com.github.catalpaflat.third.constant.ThirdLoginConstant.OPEN_ID;

/**
 * @author CatalpaFlat
 */
public class ThirdLoginQQH5Support extends ThirdLoginSupport {


    public ThirdLoginQQH5Support(ThirdPartyLoginYmlConfig thirdPartyLoginYmlConfig) {
        super(thirdPartyLoginYmlConfig);
        initThirdLoginQQ();
        initQQH5AppIdWithSecret();
    }


    public JSONObject obtainOpenId(String accessToken) throws Exception {
        String url = ThirdLoginConstant.QQ_H5_OPEN_ID + accessToken + "&unionid=1";
        String result = HttpClientSupport.get(url, "UTF-8");
         /*
            callback( {
                  "client_id":"YOUR_APPID",
                  "openid":"YOUR_OPENID",
                  "unioid":"YOUR_UNIONID"
            });
             */
        if (StringUtils.isBlank(result)) {
            throw new ThirdLoginException("qq h5 obtain open_id result is empty");
        }
        String[] split = result.split("callback");
        String s = split[1];
        String substring = s.substring(1, s.length() - 3);
        JSONObject responseJson = JSONObject.fromObject(substring);
        if (responseJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(responseJson.getJSONObject(ERRCODE)));
        }

        return responseJson;
    }

    public JSONObject obtainUserInfo(String accessToken, String openId) throws Exception {
        String appId = qqH5AppIdWithSecretTO.getAppId();

            /*
                access_token | access_token
                oauth_consumer_key | QQ_AppId
                openid | openid
             */
        String url = ThirdLoginConstant.QQ_H5_OPEN_ID +
                "?access_token=" + accessToken +
                "&oauth_consumer_key=" + appId +
                "&openid=" + openId;
        String infoResult = HttpClientSupport.get(url, "UTF-8");

        if (StringUtils.isBlank(infoResult)) {
            throw new ThirdLoginException("qq h5 obtain user info result is empty");
        }
        JSONObject infoJson = JSONObject.fromObject(infoResult);

        if (infoJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(infoJson.getJSONObject(ERRCODE)));
        }

        return infoJson;
    }


    public JSONObject obtainUserInfo(String accessToken) throws Exception {
        JSONObject openIdJson = obtainOpenId(accessToken);
        String openId = openIdJson.getString(OPEN_ID);
        String appId = qqH5AppIdWithSecretTO.getAppId();

        /*
            access_token | access_token
            oauth_consumer_key | QQ_AppId
            openid | openid
         */
        String url = ThirdLoginConstant.QQ_H5_OPEN_ID +
                "?access_token=" + accessToken +
                "&oauth_consumer_key=" + appId +
                "&openid=" + openId;
        String infoResult = HttpClientSupport.get(url, "UTF-8");

        if (StringUtils.isBlank(infoResult)) {
            throw new ThirdLoginException("qq h5 obtain user info result is empty");
        }
        JSONObject infoJson = JSONObject.fromObject(infoResult);

        if (infoJson.containsKey(ERRCODE)) {
            throw new ThirdLoginException(new Gson().toJson(infoJson.getJSONObject(ERRCODE)));
        }

        return infoJson;
    }
}
