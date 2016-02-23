package com.xuie.coolblog.mvp.model;

/**
 * Created by xuie on 15-12-28.
 */
public interface BlogModel {
    void load(String url, int type, final BlogModelImpl.OnListener listener);

    void loadDetail(String docid, final BlogModelImpl.OnLoadDetailListener listener);

    void loadLink(String link, final BlogModelImpl.OnLoadDetailListener listener);

    void loadCool(String link, final BlogModelImpl.OnLoadDetailListener listener);

}
