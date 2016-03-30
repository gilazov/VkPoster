package com.gilazovdeveloper.vkposter.model.vo;

/**
 * Created by ruslan on 13.03.16.
 */
public class Post {
    public String id;
    public String text;
    public String pictureSrc;
    public String date;
    public User owner;
    public String likes;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Post post = (Post) o;

        if (id != null ? !id.equals(post.id) : post.id != null) return false;
        if (text != null ? !text.equals(post.text) : post.text != null) return false;
        if (pictureSrc != null ? !pictureSrc.equals(post.pictureSrc) : post.pictureSrc != null)
            return false;
        if (date != null ? !date.equals(post.date) : post.date != null) return false;
        if (owner != null ? !owner.equals(post.owner) : post.owner != null) return false;
        return !(likes != null ? !likes.equals(post.likes) : post.likes != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (text != null ? text.hashCode() : 0);
        result = 31 * result + (pictureSrc != null ? pictureSrc.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (likes != null ? likes.hashCode() : 0);
        return result;
    }
}
