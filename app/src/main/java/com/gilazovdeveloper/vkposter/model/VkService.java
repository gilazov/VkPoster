package com.gilazovdeveloper.vkposter.model;

import android.util.Log;

import com.gilazovdeveloper.vkposter.callback.GetPostResultListener;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.model.vo.User;
import com.gilazovdeveloper.vkposter.utils.Utils;
import com.vk.sdk.api.VKApi;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.VKError;
import com.vk.sdk.api.VKParameters;
import com.vk.sdk.api.VKRequest;
import com.vk.sdk.api.VKResponse;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUser;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPostArray;
import com.vk.sdk.api.model.VKUsersArray;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ruslan on 18.03.16.
 */
public class VkService {
    public static final int POST_REQUEST_COUNT = 10;

    public static List<Post> getVOFromVK(VKPostArray posts, VKUsersArray users, VKApiCommunityArray groups) {
        List<Post> result = new ArrayList<>();

        for (VKApiPost currentPost: posts) {
            Post newPost = new Post();
            newPost.id = String.valueOf(currentPost.id);
            newPost.date = Utils.getHumanReadDate(currentPost.date);
            newPost.text = currentPost.text;
            newPost.likes =String.valueOf(currentPost.likes_count);

            User user = new User();
            user.id = String.valueOf(currentPost.from_id);

            VKApiUserFull vkUser =  users.getById(currentPost.from_id);

            if (vkUser!=null) {
                user.name = vkUser.first_name + " " + vkUser.last_name;
                user.photoSrc = vkUser.photo_100;
            }

            VKApiCommunityFull vkGroup = groups.getById(currentPost.from_id);

            if (vkGroup!=null) {
                user.name = vkGroup.name;
                user.photoSrc = vkGroup.photo_100;
            }

            newPost.owner = user;
            newPost.pictureSrc ="";

            VKApiPhoto vkApiPhoto = null;
            for (VKAttachments.VKApiAttachment attachment : currentPost.attachments) {
                if (attachment.getType().equals( VKApiConst.PHOTO)) {
                    vkApiPhoto = (VKApiPhoto) attachment;
                    newPost.pictureSrc =  vkApiPhoto.photo_604 ;
                }
            }

            result.add(newPost);
        }

        return result;
    }

    public static void getPostsWithListener(int offset, final GetPostResultListener listener){
        final VKRequest request = VKApi.wall().get(VKParameters.from(VKApiConst.COUNT, POST_REQUEST_COUNT,VKApiConst.OFFSET, offset,  VKApiConst.EXTENDED, 1));

        request.executeWithListener(new VKRequest.VKRequestListener() {
            @Override
            public void onComplete(VKResponse response) {
                super.onComplete(response);

                VKPostArray postsArray = null;

                try {
                    postsArray = (VKPostArray) new VKPostArray().parse(response.json);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                VKUsersArray usersArray = new VKUsersArray();

                try {
                    JSONObject responseJO = response.json.getJSONObject("response");
                    JSONArray profilesArray = responseJO.getJSONArray("profiles");
                    usersArray.fill(profilesArray, VKApiUserFull.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                VKApiCommunityArray groupsArray = new VKApiCommunityArray();

                try {
                    JSONObject responseJO = response.json.getJSONObject("response");
                    JSONArray communityArray = responseJO.getJSONArray("groups");
                    groupsArray.fill(communityArray, VKApiCommunityFull.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

              listener.onComplete(getVOFromVK(postsArray, usersArray, groupsArray));

            }

            @Override
            public void onError(VKError error) {
                super.onError(error);
              listener.onError(error);
            }
        });

    }


}
