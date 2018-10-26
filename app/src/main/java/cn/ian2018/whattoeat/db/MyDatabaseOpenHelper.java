package cn.ian2018.whattoeat.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2017/7/22/022.
 */

public class MyDatabaseOpenHelper extends SQLiteOpenHelper {

    public static final String CREATE_SHOP = "create table Shop (id integer primary key autoincrement," +
            " name text, location text, phone text, image text, type integer, menuId integer)";

    public static final String CREATE_SHOP_MENU = "create table Menu (id integer primary key autoincrement," +
            " name text, price real, shopId integer)";

    public MyDatabaseOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_SHOP);
        db.execSQL(CREATE_SHOP_MENU);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
