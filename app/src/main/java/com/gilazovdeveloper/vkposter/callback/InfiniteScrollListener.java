package com.gilazovdeveloper.vkposter.callback;

import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by ruslan on 19.03.16.
 */
public abstract class InfiniteScrollListener  extends RecyclerView.OnScrollListener {

    // The minimum number of items remaining before we should loading more.
    private static final int VISIBLE_THRESHOLD = 1;

    private final LinearLayoutManager layoutManager;
    private final InfiniteManager infiniteManager;

    public InfiniteScrollListener(@NonNull LinearLayoutManager layoutManager,
                                  @NonNull InfiniteManager infiniteManager) {
        this.layoutManager = layoutManager;
        this.infiniteManager = infiniteManager;
    }

    @Override
    public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
        // bail out if scrolling upward or already loading data
        if (dy < 0 || !infiniteManager.isCanInfinite()) return;

        final int visibleItemCount = recyclerView.getChildCount();
        final int totalItemCount = layoutManager.getItemCount();
        final int firstVisibleItem = layoutManager.findFirstVisibleItemPosition();

        if ((totalItemCount - visibleItemCount) <= (firstVisibleItem + VISIBLE_THRESHOLD)) {
            infiniteManager.setCanInfinite(false);
            onLoadMore();
        }
    }

    public abstract void onLoadMore();

}
