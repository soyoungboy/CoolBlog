package com.xuie.coolblog.utils;

import android.util.Log;

import com.xuie.coolblog.bean.Blog;
import com.xuie.coolblog.common.Urls;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

public class JsoupUtil {
    // @author wwj_748
    public static List<Blog> getContent(String str) {
        List<Blog> list = new ArrayList<>();

        // 获取文档内容
        Document doc = Jsoup.parse(str);

        // 获取class="details"的元素
        Element detail = doc.getElementsByClass("details").get(0);
        detail.select("script").remove(); // 删除每个匹配元素的DOM。

        // 获取标题
        Element title = detail.getElementsByClass("article_title").get(0);
        Blog blogTitle = new Blog();
        blogTitle.setState(Urls.DEF_BLOG_ITEM_TYPE.TITLE); // 设置状态
        blogTitle.setContent(ToDBC(title.text())); // 设置标题内容

        // 获取文章内容
        Element content = detail.select("div.article_content").get(0);

        // 获取所有标签为<a的元素
        Elements as = detail.getElementsByTag("a");
        for (int b = 0; b < as.size(); b++) {
            Element blockquote = as.get(b);
            // 改变这个元素的标记。例如,<span>转换为<div> 如el.tagName("div");。
            blockquote.tagName("bold"); // 转为粗体
        }

        Elements ss = detail.getElementsByTag("strong");
        for (int b = 0; b < ss.size(); b++) {
            Element blockquote = ss.get(b);
            blockquote.tagName("bold");
        }

        // 获取所有标签为<p的元素
        Elements ps = detail.getElementsByTag("p");
        for (int b = 0; b < ps.size(); b++) {
            Element blockquote = ps.get(b);
            blockquote.tagName("body");
        }

        // 获取所有引用元素
        Elements blockquotes = detail.getElementsByTag("blockquote");
        for (int b = 0; b < blockquotes.size(); b++) {
            Element blockquote = blockquotes.get(b);
            blockquote.tagName("body");
        }

        // 获取所有标签为<ul的元素
        Elements uls = detail.getElementsByTag("ul");
        for (int b = 0; b < uls.size(); b++) {
            Element blockquote = uls.get(b);
            blockquote.tagName("body");
        }

        // 找出粗体
        Elements bs = detail.getElementsByTag("b");
        for (int b = 0; b < bs.size(); b++) {
            Element bold = bs.get(b);
            bold.tagName("bold");
        }

        // 遍历博客内容中的所有元素
        for (int j = 0; j < content.children().size(); j++) {
            Element c = content.child(j); // 获取每个元素

            // 抽取出图片
            if (c.select("img").size() > 0) {
                Elements imgs = c.getElementsByTag("img");
                System.out.println("img");
                for (Element img : imgs) {
                    if (!img.attr("src").equals("")) {
                        Blog blogImgs = new Blog();
                        // 大图链接
                        if (!img.parent().attr("href").equals("")) {
                            blogImgs.setImgSrc(img.parent().attr("href"));
                            System.out.println("href="
                                    + img.parent().attr("href"));
//                            if (img.parent().parent().tagName().equals("p")) {
//                                // img.parent().parent().remove();
//                            }
                            img.parent().remove();
                        }
                        blogImgs.setContent(img.attr("src"));
                        blogImgs.setImgSrc(img.attr("src"));
                        System.out.println(blogImgs.getContent());
                        blogImgs.setState(Urls.DEF_BLOG_ITEM_TYPE.IMG);
                        list.add(blogImgs);
                    }
                }
            }
            c.select("img").remove();

            // 获取博客内容
            Blog blogContent = new Blog();
            blogContent.setState(Urls.DEF_BLOG_ITEM_TYPE.CONTENT);

            if (c.text().equals("")) {
                continue;
            } else if (c.children().size() == 1) {
                if (c.child(0).tagName().equals("bold")
                        || c.child(0).tagName().equals("span")) {
                    if (c.ownText().equals("")) {
                        // 小标题，咖啡色
                        blogContent.setState(Urls.DEF_BLOG_ITEM_TYPE.BOLD_TITLE);
                    }
                }
            }

            // 代码
            if (c.select("pre").attr("name").equals("code")) {
                blogContent.setState(Urls.DEF_BLOG_ITEM_TYPE.CODE);
                blogContent.setContent(ToDBC(c.outerHtml()));
            } else {
                blogContent.setContent(ToDBC(c.outerHtml()));
            }
            list.add(blogContent);
        }

        return list;
    }

    public static List<Blog> getMyContent(String str) {
//        Log.d("JsoupUtil", "--->" + str);

        List<Blog> list = new ArrayList<>();

        Log.d("JsoupUtil", "END");
        // 获取文档内容
        Document doc = Jsoup.parse(str);

        // 获取class="article-entry"的元素
        Element detail = doc.getElementsByClass("article-entry").first();

        detail.select("script").remove(); // 删除每个匹配元素的DOM。

        // 获取标题
        Element title = doc.getElementsByClass("article-header").first();
        Blog blogTitle = new Blog();
        blogTitle.setState(Urls.DEF_BLOG_ITEM_TYPE.TITLE); // 设置状态
        blogTitle.setContent(ToDBC(title.text())); // 设置标题内容

        // 获取文章内容
        Element content = doc.getElementsByClass("article-entry").first();

        // 获取所有标签为<a的元素
        Elements as = detail.getElementsByTag("a");
        for (int b = 0; b < as.size(); b++) {
            Element blockquote = as.get(b);
            // 改变这个元素的标记。例如,<span>转换为<div> 如el.tagName("div");。
            blockquote.tagName("bold"); // 转为粗体
        }

        Elements ss = detail.getElementsByTag("strong");
        for (int b = 0; b < ss.size(); b++) {
            Element blockquote = ss.get(b);
            blockquote.tagName("bold");
        }

        // 获取所有标签为<p的元素
        Elements ps = detail.getElementsByTag("p");
        for (int b = 0; b < ps.size(); b++) {
            Element blockquote = ps.get(b);
            blockquote.tagName("body");
        }

        // 获取所有引用元素
        Elements blockquotes = detail.getElementsByTag("blockquote");
        for (int b = 0; b < blockquotes.size(); b++) {
            Element blockquote = blockquotes.get(b);
            blockquote.tagName("body");
        }

        // 获取所有标签为<ul的元素
        Elements uls = detail.getElementsByTag("ul");
        for (int b = 0; b < uls.size(); b++) {
            Element blockquote = uls.get(b);
            blockquote.tagName("body");
        }

        // 找出粗体
        Elements bs = detail.getElementsByTag("b");
        for (int b = 0; b < bs.size(); b++) {
            Element bold = bs.get(b);
            bold.tagName("bold");
        }

        // 遍历博客内容中的所有元素
        for (int j = 0; j < content.children().size(); j++) {
            Element c = content.child(j); // 获取每个元素

            // 抽取出图片
            if (c.select("img").size() > 0) {
                Elements imgs = c.getElementsByTag("img");
                System.out.println("img");
                for (Element img : imgs) {
                    if (!img.attr("src").equals("")) {
                        Blog blogImgs = new Blog();
                        // 大图链接
                        if (!img.parent().attr("href").equals("")) {
                            blogImgs.setImgSrc(img.parent().attr("href"));
                            System.out.println("href="
                                    + img.parent().attr("href"));
//                            if (img.parent().parent().tagName().equals("p")) {
//                                // img.parent().parent().remove();
//                            }
                            img.parent().remove();
                        }
                        blogImgs.setContent(img.attr("src"));
                        blogImgs.setImgSrc(img.attr("src"));
                        System.out.println(blogImgs.getContent());
                        blogImgs.setState(Urls.DEF_BLOG_ITEM_TYPE.IMG);
                        list.add(blogImgs);
                    }
                }
            }
            c.select("img").remove();

            // 获取博客内容
            Blog blogContent = new Blog();
            blogContent.setState(Urls.DEF_BLOG_ITEM_TYPE.CONTENT);

            if (c.text().equals("")) {
                continue;
            } else if (c.children().size() == 1) {
                if (c.child(0).tagName().equals("bold")
                        || c.child(0).tagName().equals("span")) {
                    if (c.ownText().equals("")) {
                        // 小标题，咖啡色
                        blogContent.setState(Urls.DEF_BLOG_ITEM_TYPE.BOLD_TITLE);
                    }
                }
            }

            // 代码
            if (c.select("pre").attr("name").equals("code")) {
                blogContent.setState(Urls.DEF_BLOG_ITEM_TYPE.CODE);
                blogContent.setContent(ToDBC(c.outerHtml()));
            } else {
                blogContent.setContent(ToDBC(c.outerHtml()));
            }
            list.add(blogContent);
        }

        return list;
    }

    /**
     * 半角转换为全角 全角---指一个字符占用两个标准字符位置。 半角---指一字符占用一个标准的字符位置。
     */
    public static String ToDBC(String input) {
        char[] c = input.toCharArray();
        for (int i = 0; i < c.length; i++) {
            if (c[i] == 12288) {
                c[i] = (char) 32;
                continue;
            }
            if (c[i] > 65280 && c[i] < 65375)
                c[i] = (char) (c[i] - 65248);
        }
        return new String(c);
    }

}
