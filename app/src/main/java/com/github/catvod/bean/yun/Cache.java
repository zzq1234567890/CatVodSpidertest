package com.github.catvod.bean.yun;


import com.github.catvod.api.YunTokenHandler;
import com.github.catvod.spider.Init;
import com.github.catvod.utils.Path;
import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;

public class Cache {

    @SerializedName("user")
    private User user;


    public static Cache objectFrom(String str) {
        Cache item = new Gson().fromJson(str, Cache.class);
        return item == null ? new Cache() : item;
    }

    public User getUser() {
        return user == null ? new User("") : user;
    }

    public void setUser(User user) {
        this.user = user;
        this.save();
    }


    public void save() {
        Init.execute(() -> Path.write(YunTokenHandler.get().getCache(), toString()));
    }

    @Override
    public String toString() {
        return new Gson().toJson(this);
    }
}
