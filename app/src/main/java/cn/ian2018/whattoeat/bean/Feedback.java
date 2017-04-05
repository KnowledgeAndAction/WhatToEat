package cn.ian2018.whattoeat.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/2/27/027.
 */

public class Feedback extends BmobObject {
    private String info;
    private String phoneBrand;
    private String phoneBrandType;
    private String androidVersion;

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getPhoneBrand() {
        return phoneBrand;
    }

    public void setPhoneBrand(String phoneBrand) {
        this.phoneBrand = phoneBrand;
    }

    public String getPhoneBrandType() {
        return phoneBrandType;
    }

    public void setPhoneBrandType(String phoneBrandType) {
        this.phoneBrandType = phoneBrandType;
    }

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
    }
}
