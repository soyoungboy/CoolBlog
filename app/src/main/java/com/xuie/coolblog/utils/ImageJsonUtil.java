package com.xuie.coolblog.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xuie.coolblog.ui.fragment.image.ImageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/23
 */
public class ImageJsonUtil {
    public final static String TAG = "ImageJsonUtil";

    /**
     * 将获取到的json转换为图片列表对象
     */
    public static List<ImageBean> readJsonImageBeans(String res) {
        List<ImageBean> beans = new ArrayList<ImageBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonArray jsonArray = parser.parse(res).getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                ImageBean news = JsonUtil.deserialize(jo, ImageBean.class);
                beans.add(news);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }
}
