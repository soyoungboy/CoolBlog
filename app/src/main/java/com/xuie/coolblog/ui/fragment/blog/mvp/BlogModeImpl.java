package com.xuie.coolblog.ui.fragment.blog.mvp;

import com.xuie.coolblog.R;
import com.xuie.coolblog.ui.fragment.blog.BlogBean;
import com.xuie.coolblog.ui.fragment.blog.JokeBean;
import com.xuie.coolblog.common.Urls;
import com.xuie.coolblog.utils.JokeJsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;

/**
 * Created by xuie on 16-2-25.
 */
public class BlogModeImpl implements BlogMode {
    public static final String TAG = BlogModeImpl.class.getSimpleName();

    @Override
    public void loadCsdn(String url, final BlogModeImpl.OnListener listener) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(String response) {
                List<BlogBean> blogList = new ArrayList<>();

                Document document = Jsoup.parse(response);
                Elements elements = document.getElementsByClass("article_item");
                for (Element element : elements) {
                    String title = element.select("h1").text();
                    String description = element.select("div.article_description").text();
                    String date = element.getElementsByClass("article_manage").get(0).text();
                    String link = Urls.CSDN_SITE + element.select("h1").select("a").attr("href");

                    BlogBean blog = new BlogBean();
                    blog.setTitle(title);
                    blog.setDescription(description);
                    blog.setDate(date);
                    blog.setLink(link);
                    blog.setImgId(R.mipmap.csdn_icon);
                    blogList.add(blog);
                }
                listener.onSuccess(blogList);
            }
        });
    }

    @Override
    public void loadJoke(String url, final BlogModeImpl.OnListener listener) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(String response) {
                List<JokeBean> jokeBeanList = JokeJsonUtil.readJsonJokeBeans(response, Urls.JOKE_ID);
                List<BlogBean> blogList = new ArrayList<>();
                if (jokeBeanList == null)
                    return;
                for (JokeBean bean : jokeBeanList) {
                    BlogBean blog = new BlogBean();

                    blog.setTitle(bean.getTitle());
                    blog.setDescription(bean.getDigest());
                    blog.setDate(bean.getPtime());
                    blog.setLink(bean.getSource());
                    blog.setImgId(R.mipmap.csdn_icon);
                    blog.setImgSrc(bean.getImgsrc());
                    blog.setDocId(bean.getDocid());
                    blogList.add(blog);
                }
                listener.onSuccess(blogList);
            }
        });
    }

    @Override
    public void load9Th(String url, final BlogModeImpl.OnListener listener) {
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure(e);
            }

            @Override
            public void onResponse(String response) {
                List<BlogBean> blogList = new ArrayList<>();

                Document document = Jsoup.parse(response);
                Elements elements = document.getElementsByClass("archive-type-post");
                for (Element element : elements) {
                    String title = element.select("h1").text();
                    String description = "";
                    String link = Urls.COOL_SITE + element.select("h1").select("a").attr("href");
                    BlogBean blog = new BlogBean();
                    blog.setTitle(title);
                    blog.setDescription(description);
                    blog.setLink(link);
                    blog.setImgId(R.mipmap.author);
                    blogList.add(blog);
                }
                listener.onSuccess(blogList);
            }
        });
    }

    public interface OnListener {
        void onSuccess(List<BlogBean> list);

        void onFailure(Exception e);
    }


}
