package com.xuie.coolblog.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.xuie.coolblog.bean.JokeBean;
import com.xuie.coolblog.bean.JokeDetailBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 15/12/19
 */
public class JokeJsonUtils {

    final static String TAG = "JokeJsonUtils";

    /**
     * 将获取到的json转换为新闻列表对象
     * @param res
     * @param value
     * @return
     */
    public static List<JokeBean> readJsonJokeBeans(String res, String value) {
        List<JokeBean> beans = new ArrayList<JokeBean>();
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(value);
            if(jsonElement == null) {
                return null;
            }
            JsonArray jsonArray = jsonElement.getAsJsonArray();
            for (int i = 1; i < jsonArray.size(); i++) {
                JsonObject jo = jsonArray.get(i).getAsJsonObject();
                if (jo.has("skipType") && "special".equals(jo.get("skipType").getAsString())) {
                    continue;
                }
                if (jo.has("TAGS") && !jo.has("TAG")) {
                    continue;
                }

                if (!jo.has("imgextra")) {
                    JokeBean joke = JsonUtils.deserialize(jo, JokeBean.class);
                    beans.add(joke);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return beans;
    }

    public static JokeDetailBean readJsonDetailBeans(String res, String docId) {
        JokeDetailBean jokeDetailBean = null;
        try {
            JsonParser parser = new JsonParser();
            JsonObject jsonObj = parser.parse(res).getAsJsonObject();
            JsonElement jsonElement = jsonObj.get(docId);
            if(jsonElement == null) {
                return null;
            }
            jokeDetailBean = JsonUtils.deserialize(jsonElement.getAsJsonObject(), JokeDetailBean.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jokeDetailBean;
    }

}
