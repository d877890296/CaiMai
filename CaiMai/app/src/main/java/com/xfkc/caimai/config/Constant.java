
package com.xfkc.caimai.config;


/**
 * @ClassName: Constant
 * @Description: Constant
 */
public class Constant {

    public static final String TAG = "---幸福康城---";
    public static final String BASE_URL = "";//本地


    //关于我们
    public static final String ABOUT_US = "";
    //常见问题
    public static final String COMMON_PROBLEM = "";
    //服务条款
    public static final String FWTK_URL = "";
    //隐私相关政策
    public static final String YSXGZC_URL = "";
    //会员权益
    public static final String VIP_QY = "";


    public static final String SYS_TYPE = "1";
    public static final String SYS_VERSIN = android.os.Build.VERSION.RELEASE;

    public static final String MD5_KEY_VALUE = "";

    //短信加密  秘钥
    public static final String KEY = "";


    // 微信
    public static final String APP_ID = "wxa42ad5697b6659a2";
    public static final String WXAPP_KEY = "baed6bdb751b16df0114b76a9d6f08da";


    //支付宝授权
    public static final String PID = "";
    public static final String TARGET_ID = "";

    //全部评论页 请求id
    public static final String COMM_ID = "comm_id";
    public static final String WEB_URL = "web_url";
    public static final String VIDEO_URL = "video_url";
    public static final String VIDEO_CESHIURL = "";
    public static final String CATEGORY_ID = "category_id";//分类ID名称

    //订单分类
    public static final String ORDER_CLASS = "order_class";

    //拍照
    public static final int REQUEST_CAMERA_CODE = 10003;
    public static final int REQUEST_PREVIEW_CODE = 10004;

    //微信请求access_token
    public static final String wx_url = "https://api.weixin.qq.com/sns/oauth2/access_token?"
            +"appid="+APP_ID+
            "&secret="+WXAPP_KEY+
            "&grant_type=authorization_code&"+
            "code=";

}
