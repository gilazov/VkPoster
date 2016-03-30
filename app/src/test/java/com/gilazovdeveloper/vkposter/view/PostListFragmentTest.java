package com.gilazovdeveloper.vkposter.view;

import android.app.Activity;
import android.app.FragmentManager;
import android.os.Build;

import com.gilazovdeveloper.vkposter.BuildConfig;
import com.gilazovdeveloper.vkposter.presenter.PostListFragmentPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import static org.mockito.Mockito.verify;
import static org.robolectric.shadows.support.v4.SupportFragmentTestUtil.startFragment;

/**
 * Created by ruslan on 30.03.16.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class PostListFragmentTest {

    @Mock
    PostListFragmentPresenterImpl presenter;

    PostListFragment postListFragment;

    @Before
    public void setUp(){
        MockitoAnnotations.initMocks(this);
        MainActivity activity = Robolectric.setupActivity(MainActivity.class);
        postListFragment = PostListFragment.newInstance();
        startFragment(postListFragment);
        presenter.attachView(postListFragment);
    }

    @Test
    public void testOnDestroy(){
        postListFragment.onDestroy();
        verify(presenter).onDestroy();
    }

}
