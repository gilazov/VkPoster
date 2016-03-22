package com.gilazovdeveloper.vkposter.view;

import android.content.Context;

import com.gilazovdeveloper.vkposter.model.vo.Post;

import java.util.List;

/**
 * Created by ruslan on 13.03.16.
 */
public interface PostListFragmentView {
     void refreshData();
     void showProgress(boolean isProgress);
     void showInfiniteProgress(boolean isProgress);
     void setItems(List<Post> result, int position);
     void loadMoreItems();
     Context getActivityContext ();
     Context getApplicationContext();
     void setInfiniteEnable(boolean b);
     void showEmptyView(boolean b);
     void showErrorMessage(String errorMessage);
}
