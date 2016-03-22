package com.gilazovdeveloper.vkposter.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.gilazovdeveloper.vkposter.R;
import com.gilazovdeveloper.vkposter.callback.OnPostItemClickListener;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.utils.ImageLoader;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class PostRecyclerViewAdapter extends RecyclerView.Adapter<PostRecyclerViewAdapter.ViewHolder> {

    private final List<Post> mValues;
    OnPostItemClickListener mListener;
    Context ctx;

    public PostRecyclerViewAdapter(Context ctx, List<Post> items, OnPostItemClickListener listener) {
        mValues = items;
        this.mListener = listener;
        this.ctx = ctx;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_post_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {
        final Post post = mValues.get(position);

        holder.userName.setText(post.owner.name);
        holder.date.setText(post.date);
        holder.text.setText(post.text);
        ImageLoader.load(ctx, post.pictureSrc, holder.picture, true);
        ImageLoader.load(ctx, post.owner.photoSrc, holder.userPhoto, true);

        holder.mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null) {
                    mListener.onItemClick(post);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return mValues.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public final View mView;
        public final TextView userName;
        public final TextView date;
        public final TextView text;
        public final ImageView picture;
        public final CircleImageView userPhoto;


        public ViewHolder(View view) {
            super(view);
            mView = view;
            userName =(TextView) view.findViewById(R.id.userNameTextView);
            date =(TextView) view.findViewById(R.id.dateTextView);
            text =(TextView) view.findViewById(R.id.postTextView);
            picture = (ImageView) view.findViewById(R.id.pictureImageView);
            userPhoto = (CircleImageView) view.findViewById(R.id.userPhotoImage);
        }

    }
}
