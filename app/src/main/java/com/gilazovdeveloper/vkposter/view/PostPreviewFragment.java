package com.gilazovdeveloper.vkposter.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gilazovdeveloper.vkposter.R;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.utils.ImageLoader;
import com.gilazovdeveloper.vkposter.utils.Utils;


public class PostPreviewFragment extends Fragment {
    public Post post;

    public PostPreviewFragment() {

    }

    public static PostPreviewFragment newInstance(Post post) {
        PostPreviewFragment fragment = new PostPreviewFragment();
        fragment.post = post;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_post_preview, container, false);

        // Bind views
        TextView postTextView = (TextView) view.findViewById(R.id.postTextVIew);
        TextView userNameTextView = (TextView) view.findViewById(R.id.userNameTextView);
        TextView likesTextView = (TextView) view.findViewById(R.id.likesTextView);
        TextView postDateTextTextView = (TextView) view.findViewById(R.id.postDateTextView);
        ImageView postImageView = (ImageView) view.findViewById(R.id.pictureImageView);

        // Setup views
        if (post!=null) {
            postDateTextTextView.setText(post.date);
            likesTextView.setText( " - " + post.likes + " likes");
            userNameTextView.setText(post.owner.name);
            postTextView.setText(post.text);
            ImageLoader.load(getContext(), post.pictureSrc, postImageView, false);
        }
        return view;
    }


    @Override
    public void onDetach() {
        super.onDetach();
    }

}
