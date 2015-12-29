package com.xuie.coolblog.mvp.presenter;

/**
 * Description :
 * Author : lauren
 * Email  : lauren.liuling@gmail.com
 * Blog   : http://www.liuling123.com
 * Date   : 2015/12/21
 */
public interface JokeDetailPresenter {

    void loadDetail(String docId);
    void loadLink(String link);
    void loadCool(String link);

}
