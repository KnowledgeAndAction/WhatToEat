package cn.ian2018.whattoeat.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import cn.ian2018.whattoeat.bean.Shop;
import cn.ian2018.whattoeat.bean.ShopMenu;

/**
 * Created by Administrator on 2017/7/22/022.
 */

public class MyDatabase {
    /**
     * 数据库名
     */
    public static final String DB_NAME = "what_to_eat_shop_info";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;
    private final SQLiteDatabase db;
    private static MyDatabase myDatabase;

    private MyDatabase(Context context) {
        MyDatabaseOpenHelper databaseOpenHelper = new MyDatabaseOpenHelper(context, DB_NAME, null, VERSION);
        db = databaseOpenHelper.getWritableDatabase();
    }

    public synchronized static MyDatabase getInstance(Context context) {
        if (myDatabase == null) {
            myDatabase = new MyDatabase(context);
        }
        return myDatabase;
    }

    // 保存商店信息到数据库
    public void saveShop(Shop shop) {
        ContentValues values = new ContentValues();
        values.put("name", shop.getName());
        values.put("location", shop.getLocation());
        values.put("phone", shop.getPhone());
        values.put("image", shop.getImageUrl());
        values.put("type", shop.getType());
        values.put("menuId", shop.getId());
        db.insert("Shop", null, values);
    }

    // 保存菜单到数据库中
    public void saveMenu(ShopMenu shopMenu) {
        ContentValues values = new ContentValues();
        values.put("name", shopMenu.getName());
        values.put("price", shopMenu.getPrice());
        values.put("shopId", shopMenu.getShopId());
        db.insert("Menu", null, values);
    }

    // 根据type获取商店
    public List<Shop> getShops(int type) {
        List<Shop> shopList = new ArrayList<>();
        Cursor cursor = db.query("Shop", null, "type = ?", new String[]{String.valueOf(type)}, null, null, null);
        while (cursor.moveToNext()) {
            Shop shop = new Shop();
            shop.setName(cursor.getString(cursor.getColumnIndex("name")));
            shop.setLocation(cursor.getString(cursor.getColumnIndex("location")));
            shop.setPhone(cursor.getString(cursor.getColumnIndex("phone")));
            shop.setImageUrl(cursor.getString(cursor.getColumnIndex("image")));
            shop.setType(cursor.getInt(cursor.getColumnIndex("type")));
            shop.setId(cursor.getInt(cursor.getColumnIndex("menuId")));
            shopList.add(shop);
        }
        cursor.close();
        return shopList;
    }

    // 根据商店id获取对应的菜单
    public List<ShopMenu> getShopMenu(int shopId) {
        List<ShopMenu> shopMenuList = new ArrayList<>();
        Cursor cursor = db.query("Menu", null, "shopId = ?", new String[]{String.valueOf(shopId)}, null, null, null);
        while (cursor.moveToNext()) {
            ShopMenu shopMenu = new ShopMenu();
            shopMenu.setName(cursor.getString(cursor.getColumnIndex("name")));
            shopMenu.setPrice(cursor.getDouble(cursor.getColumnIndex("price")));
            shopMenu.setShopId(cursor.getInt(cursor.getColumnIndex("shopId")));
            shopMenuList.add(shopMenu);
        }
        cursor.close();
        return shopMenuList;
    }

    public void deleteShop(int type) {
        db.delete("Shop", "type = ?", new String[]{String.valueOf(type)});
    }

    public void deleteShopMenu(int shopId) {
        db.delete("Menu", "shopId = ?", new String[]{String.valueOf(shopId)});
    }
}
