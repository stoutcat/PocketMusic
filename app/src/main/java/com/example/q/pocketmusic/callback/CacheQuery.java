package com.example.q.pocketmusic.callback;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/4/20.
 */

public class CacheQuery<T> extends BmobQuery<T> {

    public CacheQuery(boolean isRefreshing) {
        if (isRefreshing) {
            setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);
        } else {
            setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);
        }

    }
}
