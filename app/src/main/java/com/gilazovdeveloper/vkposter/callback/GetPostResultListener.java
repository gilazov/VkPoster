package com.gilazovdeveloper.vkposter.callback;

import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.vk.sdk.api.VKError;

import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 */

public interface  GetPostResultListener {

    void onComplete(List<Post> list);

    void onError(VKError error);
}

