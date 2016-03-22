package com.gilazovdeveloper.vkposter.view;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.gilazovdeveloper.vkposter.R;
import com.gilazovdeveloper.vkposter.adapter.PostRecyclerViewAdapter;
import com.gilazovdeveloper.vkposter.callback.InfiniteManager;
import com.gilazovdeveloper.vkposter.callback.InfiniteScrollListener;
import com.gilazovdeveloper.vkposter.callback.OnPostItemClickListener;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.presenter.PostListFragmentPresenterImpl;

import java.util.List;

public class PostListFragment extends Fragment implements PostListFragmentView, OnPostItemClickListener {

    PostListFragmentPresenterImpl presenter;
    RecyclerView recyclerView;
    SwipeRefreshLayout refreshLayout;
    ProgressBar infiniteProgressBar;
    LinearLayoutManager layoutManager;
    InfiniteManager infiniteManager = new InfiniteManager();
    View view;
    TextView emptyView;
    public PostListFragment() {
    }

    @SuppressWarnings("unused")
    public static PostListFragment newInstance() {
        PostListFragment fragment = new PostListFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new PostListFragmentPresenterImpl(this);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.loadData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_post_list, container, false);
        infiniteProgressBar = (ProgressBar) view.findViewById(R.id.infiniteProgress);
        recyclerView = (RecyclerView) view.findViewById(R.id.list);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swiperefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshData();
            }
        });
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);
        emptyView = (TextView) view.findViewById(R.id.emptyView);
        recyclerView.setOnScrollListener(new InfiniteScrollListener(layoutManager, infiniteManager) {
            @Override
            public void onLoadMore() {
                loadMoreItems();
            }
        });
        return view;
    }

    @Override
    public void refreshData() {
        presenter.refreshData();
    }

    @Override
    public void showProgress(boolean isProgress) {
        if (isProgress) {
           refreshLayout.setRefreshing(true);
        }else {
            refreshLayout.setRefreshing(false);
        }
    }

    @Override
    public void showInfiniteProgress(boolean isProgress) {
        if (isProgress) {
            infiniteProgressBar.setVisibility(View.VISIBLE);
        }else {
            infiniteProgressBar.setVisibility(View.GONE);
        }
    }

    @Override
    public void setItems(List<Post> result , int scrollPosition) {
        recyclerView.setAdapter(new PostRecyclerViewAdapter(getContext(), result, this));
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scrollToPosition(scrollPosition);
    }

    @Override
    public void loadMoreItems() {
            presenter.loadMore();
    }

    @Override
    public Context getActivityContext() {
        return getActivity();
    }

    @Override
    public Context getApplicationContext() {
        return getApplicationContext();
    }

    @Override
    public void setInfiniteEnable(boolean b) {
        infiniteManager.setCanInfinite(b);
    }

    @Override
    public void showEmptyView(boolean b) {
        if (b){
            emptyView.setVisibility(View.VISIBLE);
        }else{
            emptyView.setVisibility(View.GONE);
        }
    }

    @Override
    public void showErrorMessage(String errorMessage) {
        Snackbar.make(view,errorMessage, Snackbar.LENGTH_LONG).show();
    }

    @Override
    public void onItemClick(Post post) {
        presenter.OnItemClick(post);
    }


}
