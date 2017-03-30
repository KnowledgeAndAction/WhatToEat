package cn.ian2018.whattoeat.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2016/10/24/024.
 */

public class PhoneInfo extends BmobObject {
    private String phoneBrand;
    private String phoneBrandType;
    private String androidVersion;

    public String getAndroidVersion() {
        return androidVersion;
    }

    public void setAndroidVersion(String androidVersion) {
        this.androidVersion = androidVersion;
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
}
