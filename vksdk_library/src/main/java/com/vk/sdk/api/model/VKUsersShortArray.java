package com.vk.sdk.api.model;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by ruslan on 14.03.16.
 */
public class VKUsersShortArray extends VKList<VKApiUser> {
    @Override
    public VKApiModel parse(JSONObject response) throws JSONException {
        fill(response, VKApiUser.class);
        return this;
    }
}
