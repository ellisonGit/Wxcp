package com.hnjca.wechat.util;


import com.hnjca.wechat.pojo.WXUser;

/**
 * Description:
 * User: by yangyong
 * Date: 2018-09-26
 * Time: 9:03
 * Modified:
 */
public class TicketUtil {

    private static final String key = "ju78hg56g1138j";

    public static String signWxUser(WXUser wxUser){
        /**
         * 汉字处理掉
         */
        wxUser.setNickname(null);
        wxUser.setCity(null);
        wxUser.setProvince(null);
        wxUser.setCountry(null);
        wxUser.setPrivilege(null);
        wxUser.setSex(null);
        wxUser.setHeadimgurl(null);
        wxUser.setLanguage(null);
        wxUser.setUnionid(null);

        String str = "";
        wxUser.setSignKey(null);
        String ticketStr = GsonUtil.objectToJson(wxUser);
        String signKey = MD5Util.MD5Encode(ticketStr + "&key=" + key);
        wxUser.setSignKey(signKey);
        str =  GsonUtil.objectToJson(wxUser);
        return str;
    }
}
