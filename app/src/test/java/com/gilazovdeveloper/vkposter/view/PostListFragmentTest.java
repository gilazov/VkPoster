package com.gilazovdeveloper.vkposter.view;

import com.gilazovdeveloper.vkposter.BuildConfig;
import com.gilazovdeveloper.vkposter.presenter.PostListFragmentPresenterImpl;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.lang.reflect.Field;

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
    public void setUp() throws NoSuchFieldException, IllegalAccessException {
        MockitoAnnotations.initMocks(this);
        postListFragment = PostListFragment.newInstance();
        startFragment(postListFragment);

        Field privateField = PostListFragment.class.getDeclaredField("presenter");
        privateField.setAccessible(true);
        privateField.set(postListFragment, presenter);
    }

    @Test
    public void testOnDestroy(){
        postListFragment.onDestroy();
        verify(presenter).onDestroy();
    }
}
