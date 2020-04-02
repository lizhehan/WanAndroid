package com.lizhehan.wanandroid.bean;

import android.text.Html;

public class Tool {
    /**
     * desc :
     * icon :
     * id : 4345
     * link : https://github.com/goweii/WanAndroid/issues/38
     * name : 公众号tab里的fragment里的状态是怎么保存的 &middot; Issue #38 &middot; goweii/WanAndroid &middot; GitHub
     * order : 0
     * userId : 27602
     * visible : 1
     */

    private String desc;
    private String icon;
    private int id;
    private String link;
    private String name;
    private int order;
    private int userId;
    private int visible;

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getName() {
        return Html.fromHtml(name).toString();
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getVisible() {
        return visible;
    }

    public void setVisible(int visible) {
        this.visible = visible;
    }
}
