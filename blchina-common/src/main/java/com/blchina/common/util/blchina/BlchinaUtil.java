package com.blchina.common.util.blchina;

public class BlchinaUtil {

    public final static String REDIS_PREFIX = "eds_";
    public final static String REDIS_MESSAGE_KEY = REDIS_PREFIX + "message_";

    public final static String REDIS_PAD_USER_KEY = REDIS_PREFIX + "pad_user_";

    public final static String REDIS_DOCTYPE_MAP = REDIS_PREFIX + "doctype_map";

    public final static String REDIS_APK_VERSION_PRIFIX = REDIS_PREFIX + "apk_version_";
    public final static int TTL_APK_VERSION =  10 * 60 * 60;



    public final static String REDIS_APK_INFO_PRIFIX = REDIS_PREFIX + "apk_info_";

    public final static int TTL_DOCTYPE_MAP =  5 * 60;     //

    public final static int TTL_PAD_USER =  4 * 60 * 60;     // 5分钟

    public final static int TTL_WENXI_USER = 2*60 * 60;     // 微信token有效时间2小时

    public final static int MESSAGE_SEND_EXPIRE =  2 * 60 * 1000;     // 消息推送5分钟间隔

    public final static int MESSAGE_INVALID_EXPIRE =  120 * 60 * 1000;     // 消息1个小时失效

    public final static String SUCCESS = "1";
    public final static String FAIL = "0";
    public final static int HAVE_PHOTO = 1;
    public final static int NO_PHOTO = 0;

    public final static String APK_EDS = "EDS";
    public final static String APK_CBP = "CBP";
    public final static String APK_HUANCHE = "TestCar";

    public final static String APK_SUBFIX = ".apk";

}
