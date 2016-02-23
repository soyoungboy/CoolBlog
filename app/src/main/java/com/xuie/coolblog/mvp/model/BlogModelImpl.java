package com.xuie.coolblog.mvp.model;

import com.xuie.coolblog.R;
import com.xuie.coolblog.bean.Blog;
import com.xuie.coolblog.bean.JokeBean;
import com.xuie.coolblog.bean.JokeDetailBean;
import com.xuie.coolblog.common.Urls;
import com.xuie.coolblog.utils.JokeJsonUtils;
import com.xuie.coolblog.utils.JsoupUtil;
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
 * Created by xuie on 15-12-28.
 */
public class BlogModelImpl implements BlogModel {
    static final String TAG = BlogModelImpl.class.getSimpleName();

    public interface OnListener {
        void onSuccess(List<Blog> list);

        void onFailure(Exception e);
    }

    public interface OnLoadDetailListener {
        void onSuccess(JokeDetailBean jokeDetailBean);

        void onFailure(String msg, Exception e);
    }

    @Override
    public void load(String url, int type, final OnListener listener) {
        switch (type) {
            case 0:
                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        List<Blog> blogList = new ArrayList<>();

                        Document document = Jsoup.parse(response);
                        Elements elements = document.getElementsByClass("article_item");
                        for (Element element : elements) {
                            String title = element.select("h1").text();
                            String description = element.select("div.article_description").text();
                            String date = element.getElementsByClass("article_manage").get(0).text();
                            String link = Urls.CSDN_SITE + element.select("h1").select("a").attr("href");

                            Blog blog = new Blog();
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
                break;
            case 1:
                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        List<JokeBean> jokeBeanList = JokeJsonUtils.readJsonJokeBeans(response, Urls.JOKE_ID);
                        List<Blog> blogList = new ArrayList<>();
                        if (jokeBeanList == null)
                            return;
                        for (JokeBean bean : jokeBeanList) {
                            Blog blog = new Blog();

                            blog.setTitle(bean.getTitle());
                            blog.setDescription(bean.getDigest());
                            blog.setDate(bean.getPtime());
                            blog.setLink(bean.getSource());
                            blog.setImgId(R.mipmap.csdn_icon);
                            blog.setImgSrc(bean.getImgsrc());
                            blog.setDocid(bean.getDocid());
                            blogList.add(blog);
                        }
                        listener.onSuccess(blogList);
                    }
                });
                break;
            case 2:
                OkHttpUtils.get().url(url).build().execute(new StringCallback() {
                    @Override
                    public void onError(Call call, Exception e) {
                        listener.onFailure(e);
                    }

                    @Override
                    public void onResponse(String response) {
                        List<Blog> blogList = new ArrayList<>();

                        Document document = Jsoup.parse(response);
                        Elements elements = document.getElementsByClass("archive-type-post");
                        for (Element element : elements) {
                            String title = element.select("h1").text();
                            String description = "";
                            String link = Urls.COOL_SITE + element.select("h1").select("a").attr("href");
                            Blog blog = new Blog();
                            blog.setTitle(title);
                            blog.setDescription(description);
                            blog.setLink(link);
                            blog.setImgId(R.mipmap.author);
                            blogList.add(blog);
                        }
                        listener.onSuccess(blogList);
                    }
                });
                break;
        }
    }

    @Override
    public void loadDetail(final String docId, final OnLoadDetailListener listener) {
        String url = getDetailUrl(docId);
        OkHttpUtils.get().url(url).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure("load joke detail info failure.", e);
            }

            @Override
            public void onResponse(String response) {
                JokeDetailBean jokeDetailBean = JokeJsonUtils.readJsonDetailBeans(response, docId);
                listener.onSuccess(jokeDetailBean);
            }
        });
    }

    @Override
    public void loadLink(String link, final OnLoadDetailListener listener) {
        OkHttpUtils.get().url(link).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure("load csdn detail info failure.", e);
            }

            @Override
            public void onResponse(String response) {
                List<Blog> blogList = JsoupUtil.getContent(response);
                if (blogList.size() > 0) {
                    JokeDetailBean jokeDetailBean = new JokeDetailBean();
                    for (Blog blog : blogList) {
                        jokeDetailBean.setBody(jokeDetailBean.getBody() + blog.getContent());
                    }
                    listener.onSuccess(jokeDetailBean);
                }
            }
        });
    }

    @Override
    public void loadCool(String link, final OnLoadDetailListener listener) {
        OkHttpUtils.get().url(link).build().execute(new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {
                listener.onFailure("load coolxuj detail info failure.", e);
            }

            @Override
            public void onResponse(String response) {
                List<Blog> blogList = JsoupUtil.getMyContent(response);

                if (blogList.size() > 0) {
                    JokeDetailBean jokeDetailBean = new JokeDetailBean();
                    for (Blog blog : blogList) {
                        jokeDetailBean.setBody(jokeDetailBean.getBody() + blog.getContent());
                    }
                    listener.onSuccess(jokeDetailBean);
                }
            }
        });

    }

    private String getDetailUrl(String docId) {
        return Urls.NEW_DETAIL + docId + Urls.END_DETAIL_URL;
    }

}
