package cn.ian2018.whattoeat.utils;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;
import cn.ian2018.whattoeat.bean.PhoneInfo;
import cn.ian2018.whattoeat.bean.Shop;
import cn.ian2018.whattoeat.bean.ShopMenu;

/**
 * Created by Administrator on 2017/3/30/030.
 */

public class HttpUtil {
    /**
     * 从Bmob查询数据
     * @param type  商铺类型 1是叫外卖 2是到店吃
     * @param list  数据集合
     * @param result    结果回调接口
     */
    public static void getData(int type, final List<Shop> list, final OnResult result) {
        BmobQuery<Shop> query = new BmobQuery<>();
        query.addWhereEqualTo("type", type);
        // 返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1000);
        // 执行查询方法
        query.findObjects(new FindListener<Shop>() {
            @Override
            public void done(List<Shop> object, BmobException e) {
                if (e == null) {
                    for (Shop shops : object) {
                        Shop shop = new Shop();
                        shop.setImageUrl(shops.getImageUrl());
                        shop.setLocation(shops.getLocation());
                        shop.setPhone(shops.getPhone());
                        shop.setName(shops.getName());
                        shop.setId(shops.getId());
                        list.add(shop);
                    }
                    result.success();
                } else {
                    Shop shop = new Shop();
                    shop.setName("加载失败");
                    shop.setPhone("1024");
                    shop.setLocation("加载失败");
                    shop.setImageUrl("http://omean34je.bkt.clouddn.com/image/shops/error.png");
                    list.add(shop);
                    result.error();
                }
            }
        });
    }

    public static void getMenuData(int shopId, final List<ShopMenu> list, final OnResult result) {
        BmobQuery<ShopMenu> query = new BmobQuery<>();
        query.addWhereEqualTo("shopId", shopId);
        // 返回50条数据，如果不加上这条语句，默认返回10条数据
        query.setLimit(1000);
        // 执行查询方法
        query.findObjects(new FindListener<ShopMenu>() {
            @Override
            public void done(List<ShopMenu> object, BmobException e) {
                if (e == null) {
                    for (ShopMenu menu : object) {
                        ShopMenu shopMenu = new ShopMenu();
                        shopMenu.setName(menu.getName());
                        shopMenu.setPrice(menu.getPrice());
                        shopMenu.setShopId(menu.getShopId());
                        list.add(shopMenu);
                    }
                    result.success();
                } else {
                    ShopMenu shopMenu = new ShopMenu();
                    shopMenu.setName("加载失败");
                    shopMenu.setPrice(0);
                    list.add(shopMenu);
                    result.error();
                }
            }
        });
    }

    public static void sendPhoneInfo(PhoneInfo phoneInfo) {
        PhoneInfo phone = new PhoneInfo();
        phone.setPhoneBrand(phoneInfo.getPhoneBrand());
        phone.setPhoneBrandType(phoneInfo.getPhoneBrandType());
        phone.setAndroidVersion(phoneInfo.getAndroidVersion());
        phone.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e==null){
                    Logs.i("上传手机信息成功");
                }else{
                    Logs.i("上传手机信息失败："+e.getMessage()+","+e.getErrorCode());
                }
            }
        });
    }

    public interface OnResult {
        void success();
        void error();
    }
}
