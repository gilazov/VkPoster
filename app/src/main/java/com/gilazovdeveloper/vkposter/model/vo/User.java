package com.gilazovdeveloper.vkposter.model.vo;

/**
 * Created by ruslan on 13.03.16.
 */
public class User {
    public String id;
    public String name;
    public String photoSrc;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        User user = (User) o;

        if (id != null ? !id.equals(user.id) : user.id != null) return false;
        if (name != null ? !name.equals(user.name) : user.name != null) return false;
        return !(photoSrc != null ? !photoSrc.equals(user.photoSrc) : user.photoSrc != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (photoSrc != null ? photoSrc.hashCode() : 0);
        return result;
    }
}
