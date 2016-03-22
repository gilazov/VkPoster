package com.gilazovdeveloper.vkposter.utils;

import android.support.v4.util.LruCache;

import com.gilazovdeveloper.vkposter.model.vo.Post;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 */
public class PostLruCacheImpl implements PostCache {
    private LruCache<String, Post> mMemoryCache;

    public PostLruCacheImpl() {
        //working with cache initializing
        final int maxMemory = (int) (Runtime.getRuntime().maxMemory() / 1024);

        // Use 1/8th of the available memory for this memory cache.
        final int cacheSize = maxMemory / 8;

        mMemoryCache = new LruCache<>(cacheSize);

    }

    public void add(String key, Post post) {
        if (get(key) == null) {
            mMemoryCache.put(key, post);
        }
    }

    public Post get(String key) {
        return mMemoryCache.get(key);
    }

    public void addAll(List<Post> list) {
        for (Post post: list) {
            mMemoryCache.put(post.id, post);
        }
        }



    public void clearCache(){
        mMemoryCache.evictAll();
    }

    public int getSize(){
        return mMemoryCache.size();
    }

    public List<Post> getAll(){
            List<Post> list = new ArrayList<>();
            list.addAll(mMemoryCache.snapshot().values());
        return list;
    }

}
