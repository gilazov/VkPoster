package com.gilazovdeveloper.vkposter.presenter;

import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.utils.PostCache;
import com.gilazovdeveloper.vkposter.view.PostListFragmentView;

/**
 * Created by ruslan on 13.03.16.
 */
public interface PostListFragmentPresenter {

    void attachView(PostListFragmentView view);

    void loadData();//загрузка того что есть в кеше если он не пуст

    void loadMore();// infinite scroll

    void refreshData(); // обновление кеша новыми данными с сети

    void onDestroy();

    void OnItemClick(Post post);


}
