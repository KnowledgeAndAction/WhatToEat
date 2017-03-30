package cn.ian2018.whattoeat.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by Administrator on 2017/3/30/030.
 */

public class ShopMenu extends BmobObject {
    private int shopId;
    private String name;
    private double price;

    public int getShopId() {
        return shopId;
    }

    public void setShopId(int shopId) {
        this.shopId = shopId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
