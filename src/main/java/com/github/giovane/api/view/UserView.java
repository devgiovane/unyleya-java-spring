package com.github.giovane.api.view;


import com.google.gson.Gson;
import java.util.List;

import com.github.giovane.api.entity.UserEntity;


public class UserView {

    public String showUser(UserEntity userEntity) {
        return new Gson().toJson(userEntity);
    }

    public String showUsers(List<UserEntity> userEntities) {
        return new Gson().toJson(userEntities);
    }

}
