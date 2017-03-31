package cn.ian2018.whattoeat.bean;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by Administrator on 2017/3/31/031.
 */

public class ExceptionFile extends BmobObject {
    private BmobFile exceptionFile;
    private String phoneBrand;
    private String phoneBrandType;
    private String androidVersion;

    public BmobFile getExceptionFile() {
        return exceptionFile;
    }

    public void setExceptionFile(BmobFile exceptionFile) {
        this.exceptionFile = exceptionFile;
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
