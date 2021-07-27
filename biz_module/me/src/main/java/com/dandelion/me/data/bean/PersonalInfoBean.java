package com.dandelion.me.data.bean;

import com.dandelion.network.bean.IBean;


public class PersonalInfoBean implements IBean {
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
