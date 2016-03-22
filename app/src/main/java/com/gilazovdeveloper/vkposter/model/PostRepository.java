package com.gilazovdeveloper.vkposter.model;

import com.gilazovdeveloper.vkposter.callback.GetPostResultListener;
import com.gilazovdeveloper.vkposter.callback.OnFinishedListener;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.utils.PostCache;
import com.gilazovdeveloper.vkposter.utils.PostLruCacheImpl;
import com.vk.sdk.api.VKError;

import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public class PostRepository {

    public static final int FULL_DATA_RESPONSE = 0;
    public static final int MORE_DATA_RESPONSE = 1;
    public static final int MORE_DATA_RESPONSE_END_OF_LIST = 2;

    PostCache mCache;

    public PostRepository() {
        //set Default Cache
        setCache(new PostLruCacheImpl());
    }

    public void setCache(PostCache cache){
        this.mCache = cache;
    }

    public void getPosts(final OnFinishedListener onFinishedListener){

        if (mCache.getSize()!=0){
            onFinishedListener.onFinished(mCache.getAll(), FULL_DATA_RESPONSE);
        }else{
            VkService.getPostsWithListener(0, new GetPostResultListener() {
                @Override
                public void onComplete(List<Post> list) {
                    mCache.addAll(list);
                    onFinishedListener.onFinished(list, FULL_DATA_RESPONSE);
                }

                @Override
                public void onError(VKError error) {
                    onFinishedListener.onError(error.errorMessage);
                }
            });
        }

    }

    public void refreshData(final OnFinishedListener onFinishedListener) {

        VkService.getPostsWithListener(0, new GetPostResultListener() {
            @Override
            public void onComplete(List<Post> list) {
                mCache.clearCache();
                mCache.addAll(list);
                onFinishedListener.onFinished(list, FULL_DATA_RESPONSE);
            }

            @Override
            public void onError(VKError error) {
                onFinishedListener.onFinished(mCache.getAll(), FULL_DATA_RESPONSE);
            }
        });

    }

    public void getMoreData (int offset, final OnFinishedListener onFinishedListener) {
        VkService.getPostsWithListener(offset, new GetPostResultListener() {
            @Override
            public void onComplete(List<Post> list) {
                if (list.size() == 0) {
                    onFinishedListener.onFinished(list, MORE_DATA_RESPONSE_END_OF_LIST);
                }else {
                    mCache.addAll(list);
                    onFinishedListener.onFinished(list, MORE_DATA_RESPONSE);
                }
            }

            @Override
            public void onError(VKError error) {
                onFinishedListener.onError(error.errorMessage);

            }
        });
    }



}
