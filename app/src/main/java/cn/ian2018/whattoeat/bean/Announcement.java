package cn.ian2018.whattoeat.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/4/10/010.
 */

public class Announcement extends BmobObject {
    private String announcement;
    private String imageUrl;

    public String getAnnouncement() {
        return announcement;
    }

    public void setAnnouncement(String announcement) {
        this.announcement = announcement;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
