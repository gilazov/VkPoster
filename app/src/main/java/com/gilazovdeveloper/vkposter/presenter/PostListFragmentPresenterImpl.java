package com.gilazovdeveloper.vkposter.presenter;

import android.content.Context;
import android.util.Log;

import com.gilazovdeveloper.vkposter.Application;
import com.gilazovdeveloper.vkposter.callback.OnFinishedListener;
import com.gilazovdeveloper.vkposter.model.PostRepository;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.utils.PostCache;
import com.gilazovdeveloper.vkposter.utils.PostLruCacheImpl;
import com.gilazovdeveloper.vkposter.view.MainActivity;
import com.gilazovdeveloper.vkposter.view.PostListFragment;
import com.gilazovdeveloper.vkposter.view.PostListFragmentView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public class PostListFragmentPresenterImpl implements PostListFragmentPresenter, OnFinishedListener {

    PostListFragmentView view;
    PostRepository postRepository;
    List<Post> viewPosts;

    public PostListFragmentPresenterImpl(PostListFragmentView view) {
        this.view = view;
        postRepository = new PostRepository();
        postRepository.setCache(new PostLruCacheImpl());
        if (viewPosts==null) {
            viewPosts = new ArrayList<>();
        }
    }

    public PostCache getApplicationCache(){
        return ((Application)view.getApplicationContext()).getCacheSingleton();
    }

    @Override
    public void attachView(PostListFragmentView view) {
            this.view = view;
    }

    @Override
    public void loadData() {
        if (view!=null) {
            view.showProgress(true);
        }

        if (viewPosts.size() == 0) {
            postRepository.getPosts(this);
        }else {
            if (view!=null) {
                view.setItems(viewPosts, 0);
                view.showProgress(false);
            }
        }
    }

    @Override
    public void loadMore() {
        if (view!=null) {
            view.showInfiniteProgress(true);
        }
        postRepository.getMoreData(viewPosts.size(), this);
    }


    @Override
    public void refreshData() {
        if (view!=null) {
            view.showProgress(true);
        }
        postRepository.refreshData(this);
    }

    @Override
    public void onDestroy() {
        view = null;
    }

    @Override
    public void OnItemClick(Post post) {
        if (view!=null) {
            MainActivity mainActivity = (MainActivity) view.getActivityContext();
            mainActivity.startPostPreviewFragment(post);
        }
    }

    @Override
    public void onFinished(List<Post> items, int responseCode) {
        switch (responseCode) {
            case PostRepository.FULL_DATA_RESPONSE :
                if (items.size()!=0) {
                    view.setItems(items, 0);
                    viewPosts.clear();
                    viewPosts.addAll(items);
                    view.setInfiniteEnable(true);
                    view.showEmptyView(false);
                }else {
                    view.showEmptyView(true);
                    view.setInfiniteEnable(false);
                }
                view.showProgress(false);
                break;
            case PostRepository.MORE_DATA_RESPONSE:
                int offset = viewPosts.size();
                viewPosts.addAll(items);
                view.setItems(viewPosts, offset);
                view.showInfiniteProgress(false);
                view.setInfiniteEnable(true);
                break;
            case PostRepository.MORE_DATA_RESPONSE_END_OF_LIST:
                view.setInfiniteEnable(false);
                break;
        }
    }

    @Override
    public void onError(String errorMessage) {
            view.showErrorMessage(errorMessage);
   }

}
