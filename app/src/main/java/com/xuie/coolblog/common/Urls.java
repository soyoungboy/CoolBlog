package com.xuie.coolblog.common;

/**
 * Created by xuie on 15-12-28.
 */
public class Urls {
    public static final int PAGE_SIZE = 20;
    public static final String HOST = "http://c.m.163.com/";
    public static final String END_URL = "-" + PAGE_SIZE + ".html";
    public static final String END_DETAIL_URL = "/full.html";
    // http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html
    public static final String TOP_LIST = "http://c.m.163.com/nc/article/headline/T1348647909107/0-20.html";
    public static final String CSDN_HOME_LIST = "http://blog.csdn.net/xuie0000?viewmode=list";
    public static final String CSDN_ANDROID_LIST = "http://blog.csdn.net/xuie0000/article/category/5652591";
    public static final String JOKE_LIST = "http://c.m.163.com/nc/article/list/T1350383429665/0-20.html";
    public static final String JOKE_ID = "T1350383429665";
    public static final String XUIE_BLOG_LIST = "http://xuie0000.com/archives/";
    public static final String CSDN_SITE = "http://blog.csdn.net";
    public static final String COOL_SITE = "http://xuie0000.com";

    public static final String NEW_DETAIL = HOST + "nc/article/";

    // 博客每一项的类型
    public class DEF_BLOG_ITEM_TYPE {
        public static final int TITLE = 1; // 标题
        public static final int SUMMARY = 2; // 摘要
        public static final int CONTENT = 3; // 内容
        public static final int IMG = 4; // 图片
        public static final int BOLD_TITLE = 5; // 加粗标题
        public static final int CODE = 6; // 代码
    }


    // 图片
    public static final String IMAGES_URL = "http://api.laifudao.com/open/tupian.json";

    // 天气预报url
    public static final String WEATHER = "http://wthrcdn.etouch.cn/weather_mini?city=";

    //百度定位
    public static final String INTERFACE_LOCATION = "http://api.map.baidu.com/geocoder";


}
