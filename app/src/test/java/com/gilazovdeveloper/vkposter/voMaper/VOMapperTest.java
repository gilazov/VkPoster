package com.gilazovdeveloper.vkposter.voMaper;

import android.os.Parcel;

import com.gilazovdeveloper.vkposter.BuildConfig;
import com.gilazovdeveloper.vkposter.model.VkService;
import com.gilazovdeveloper.vkposter.model.vo.Post;
import com.gilazovdeveloper.vkposter.model.vo.User;
import com.gilazovdeveloper.vkposter.utils.Utils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.vk.sdk.api.VKApiConst;
import com.vk.sdk.api.model.VKApiCommunityArray;
import com.vk.sdk.api.model.VKApiCommunityFull;
import com.vk.sdk.api.model.VKApiPhoto;
import com.vk.sdk.api.model.VKApiPost;
import com.vk.sdk.api.model.VKApiUserFull;
import com.vk.sdk.api.model.VKAttachments;
import com.vk.sdk.api.model.VKPostArray;
import com.vk.sdk.api.model.VKUsersArray;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by ruslan on 30.03.16.
 */
@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class)
public class VOMapperTest {

    String jsonPostText = "{\n" +
            "  id: 2311,\n" +
            "  from_id: 63173905,\n" +
            "  owner_id: 63173905,\n" +
            "  date: 1459369451,\n" +
            "  post_type: 'post',\n" +
            "  text: '',\n" +
            "  can_edit: 1,\n" +
            "  can_delete: 1,\n" +
            "  can_pin: 1,\n" +
            "  attachments: [{\n" +
            "    type: 'photo',\n" +
            "    photo: {\n" +
            "      id: 394755920,\n" +
            "      album_id: -7,\n" +
            "      owner_id: 63173905,\n" +
            "      photo_75: 'https://pp.vk.me/...935/cVFsVxLW9a4.jpg',\n" +
            "      photo_130: 'https://pp.vk.me/...936/bJClbqj3RoM.jpg',\n" +
            "      photo_604: 'https://pp.vk.me/...937/9WlhDYhoFgI.jpg',\n" +
            "      photo_807: 'https://pp.vk.me/...938/BUqYOOohbHs.jpg',\n" +
            "      photo_1280: 'https://pp.vk.me/...939/vq3QRqQ6Kik.jpg',\n" +
            "      width: 1280,\n" +
            "      height: 853,\n" +
            "      text: '',\n" +
            "      date: 1450289837,\n" +
            "      post_id: 2310,\n" +
            "      access_key: 'ef536fa07cab731b97'\n" +
            "    }\n" +
            "  }],\n" +
            "  post_source: {\n" +
            "    type: 'vk'\n" +
            "  },\n" +
            "  comments: {\n" +
            "    count: 0,\n" +
            "    can_post: 0\n" +
            "  },\n" +
            "  likes: {\n" +
            "    count: 0,\n" +
            "    user_likes: 0,\n" +
            "    can_like: 1,\n" +
            "    can_publish: 0\n" +
            "  },\n" +
            "  reposts: {\n" +
            "    count: 0,\n" +
            "    user_reposted: 0\n" +
            "  }\n" +
            "}";

    String jsonUserFull = "{\n" +
            "  id: 63173905,\n" +
            "  first_name: 'Руслан',\n" +
            "  last_name: 'Гилазов',\n" +
            "  sex: 2,\n" +
            "  screen_name: 'ruslan_gilazov',\n" +
            "  photo_50: 'https://pp.vk.me/...8be/v-B4h2fgDJk.jpg',\n" +
            "  photo_100: 'https://pp.vk.me/...8bd/vTCcmOfVc9I.jpg',\n" +
            "  online: 1\n" +
            "}";

    @Test
    public void testMapper () throws JSONException {
        //actualVkObjects
        VKPostArray actualPosts = new VKPostArray();
        VKUsersArray actualUsers = new VKUsersArray();
        VKApiCommunityArray actualGroup = new VKApiCommunityArray();

        VKApiUserFull vkApiUserFull = new VKApiUserFull(new JSONObject(jsonUserFull));
        VKApiPost vkApiPost = new VKApiPost(new JSONObject(jsonPostText));

        actualPosts.add(vkApiPost);
        actualUsers.add(vkApiUserFull);

        //expected User
        User user =new User();
        user.id = "63173905";
        user.name = "Руслан Гилазов";
        user.photoSrc = "https://pp.vk.me/...8bd/vTCcmOfVc9I.jpg";

        //expected Post
        Post expectedPost = new Post();
        expectedPost.id = "2311";
        expectedPost.text = "";
        expectedPost.pictureSrc = "https://pp.vk.me/...937/9WlhDYhoFgI.jpg";
        expectedPost.date = Utils.getHumanReadDate(1459369451);
        expectedPost.likes = "0";
        expectedPost.owner = user;

        //actualPost
        List<Post> actualList = VkService.getVOFromVK(actualPosts,actualUsers, actualGroup);
        Post actualPost = actualList.get(0);

        assertEquals(actualPost, expectedPost);
    }
}
