package com.dandelion.communication.login.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.alibaba.fastjson.annotation.JSONField;
import com.dandelion.network.bean.IBean;

/**
 * 用户信息
 * Created by lin.wang on 2021/6/24.
 */
public class UserInfoBean implements IBean, Parcelable {
    @JSONField(name = "uid")
    private String uid;


    public UserInfoBean() {
    }

    protected UserInfoBean(Parcel in) {
        uid = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(uid);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<UserInfoBean> CREATOR = new Creator<UserInfoBean>() {
        @Override
        public UserInfoBean createFromParcel(Parcel in) {
            return new UserInfoBean(in);
        }

        @Override
        public UserInfoBean[] newArray(int size) {
            return new UserInfoBean[size];
        }
    };

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

}

