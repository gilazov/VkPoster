package com.gilazovdeveloper.vkposter.utils;

import com.gilazovdeveloper.vkposter.model.vo.Post;

import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 */
public interface PostCache {
    void clearCache();
    List<Post> getAll();
    void addAll(List<Post> list);
    int getSize();
}
