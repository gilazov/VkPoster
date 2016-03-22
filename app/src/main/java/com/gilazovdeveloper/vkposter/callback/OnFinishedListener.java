package com.gilazovdeveloper.vkposter.callback;

import com.gilazovdeveloper.vkposter.model.vo.Post;

import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public interface OnFinishedListener {
    void onFinished(List<Post> items, int responseCode);
    void onError(String errorMessage);
}
