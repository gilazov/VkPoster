package com.gilazovdeveloper.vkposter.presenter;

import com.gilazovdeveloper.vkposter.callback.OnFinishedListener;
import com.gilazovdeveloper.vkposter.model.PostRepository;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.view.PostListFragment;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.runners.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.xml.transform.Result;

import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.intThat;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by ruslan on 30.03.16.
 */
@RunWith(MockitoJUnitRunner.class)
public class PostListFragmentPresenterTest {

    public static List<Post> ANY_RESULT = new ArrayList<>();
    public static final List<Post> EMPTY_RESULT = Collections.emptyList();
    public static final String ANY_ERROR = "ERROR";
    public static final int SCROLL_TO_POSITION = 0;

    @Mock
    PostListFragment view;
    @Mock
    PostRepository repository;

    ArgumentCaptor<OnFinishedListener> listenerCaptor;
    PostListFragmentPresenterImpl presenter;

    @Before
    public void setUp(){
        presenter = new PostListFragmentPresenterImpl(view);
        presenter.attachRepository(repository);

        ANY_RESULT.add(new Post());

        listenerCaptor = ArgumentCaptor.forClass(OnFinishedListener.class);
    }

    @Test
    public void testNotEmptyListResultWhenLoadData(){
        presenter.loadData();
        verify(repository).getPosts(listenerCaptor.capture());
        OnFinishedListener listener = listenerCaptor.getValue();
        listener.onFinished(ANY_RESULT, PostRepository.FULL_DATA_RESPONSE);
        verify(view).setItems(ANY_RESULT, SCROLL_TO_POSITION);
    }

    @Test
    public void testNonEmptyResultWhenLoadMoreData(){
        presenter.loadMore();
        verify(repository).getMoreData(eq(presenter.viewPosts.size()), listenerCaptor.capture());
       // verify(repository).getMoreData(eq(23), listenerCaptor.capture());
        OnFinishedListener listener = listenerCaptor.getValue();
        listener.onFinished(ANY_RESULT, PostRepository.MORE_DATA_RESPONSE);
        verify(view).setItems(ANY_RESULT,0);
    }

    @Test
    public void testEmptyListResultWhenLoadData(){
        presenter.loadData();
        verify(repository).getPosts(listenerCaptor.capture());
        OnFinishedListener listener = listenerCaptor.getValue();
        listener.onFinished(EMPTY_RESULT, PostRepository.FULL_DATA_RESPONSE);
        verify(view).showEmptyView(true);
    }

    @Test
    public void testErrorResultWhenLoadData(){
        presenter.loadData();
        verify(repository).getPosts(listenerCaptor.capture());
        OnFinishedListener listener = listenerCaptor.getValue();
        listener.onError(ANY_ERROR);
        verify(view).showErrorMessage(ANY_ERROR);
    }

    @Test
    public void testShowProgressWhenLoadData(){
        presenter.loadData();
        verify(view).showProgress(true);
    }

    @Test
    public void testHideProgressWhenLoadDataFinish(){
        presenter.loadData();
        verify(repository).getPosts(listenerCaptor.capture());
        OnFinishedListener listener = listenerCaptor.getValue();
        listener.onFinished(ANY_RESULT, PostRepository.FULL_DATA_RESPONSE);
        verify(view).showProgress(false);
        }

    @Test
    public void testShowInfiniteProgressWhenLoadMoreData(){
        presenter.loadMore();
        verify(view).showInfiniteProgress(true);
    }

    @Test
    public void testHideInfiniteProgressWhenLoadMoreDataFinish(){
        presenter.loadMore();
        verify(repository).getMoreData(anyInt(), listenerCaptor.capture());
        OnFinishedListener listener = listenerCaptor.getValue();
        listener.onFinished(ANY_RESULT, PostRepository.MORE_DATA_RESPONSE);
        verify(view).showInfiniteProgress(false);
        listener.onFinished(EMPTY_RESULT, PostRepository.MORE_DATA_RESPONSE_END_OF_LIST);
        verify(view).showInfiniteProgress(false);
    }


    @Test
    public void testHideProgressBarsWhenErrorResult(){
        presenter.loadData();
        verify(repository).getPosts(listenerCaptor.capture());
        OnFinishedListener listener = listenerCaptor.getValue();
        listener.onError(ANY_ERROR);
        verify(view).showProgress(false);
        verify(view).showInfiniteProgress(false);
    }

}
