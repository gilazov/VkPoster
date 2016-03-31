package com.gilazovdeveloper.vkposter.utils;

/**
 * Created by ruslan on 31.03.16.
 */
public class MyCache {
    static PostLruCacheImpl mCache =  new PostLruCacheImpl();;
    public static PostLruCacheImpl getCacheInstance(){
        return mCache;
    }
}
