package cn.ian2018.whattoeat.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/2/27/027.
 */

public class UpdateFile extends BmobObject {
    private int version;
    private BmobFile apkFile;
    private String description;
    private String headImage;
    private String announcementTitle;
    private boolean isAnnouncement;

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public BmobFile getApkFile() {
        return apkFile;
    }

    public void setApkFile(BmobFile apkFile) {
        this.apkFile = apkFile;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getHeadImage() {
        return headImage;
    }

    public void setHeadImage(String headImage) {
        this.headImage = headImage;
    }

    public boolean isAnnouncement() {
        return isAnnouncement;
    }

    public void setAnnouncement(boolean announcement) {
        isAnnouncement = announcement;
    }

    public String getAnnouncementTitle() {
        return announcementTitle;
    }

    public void setAnnouncementTitle(String announcementTitle) {
        this.announcementTitle = announcementTitle;
    }
}
