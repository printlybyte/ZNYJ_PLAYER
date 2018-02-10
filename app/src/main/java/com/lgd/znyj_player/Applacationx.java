package com.lgd.znyj_player;

import android.app.Application;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.lgd.znyj_player.greendao.gen.DaoMaster;
import com.lgd.znyj_player.greendao.gen.DaoSession;

/**
 * Created by Administrator on 2018/2/7.
 */

public class Applacationx extends Application {

    private static DaoSession daoSession;
   private  static Context mContext;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
        setupDatabase();
    }

    public static Context getAppContext() {
        return  mContext;
    }

    /**
     * 配置数据库
     */
    private void setupDatabase() {
        //创建数据库shop.db" 创建SQLite数据库的SQLiteOpenHelper的具体实现
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "znyj-db", null);
        //获取可写数据库
        SQLiteDatabase db = helper.getWritableDatabase();
        //获取数据库对象  GreenDao的顶级对象，作为数据库对象、用于创建表和删除表
        DaoMaster daoMaster = new DaoMaster(db);
        //获取Dao对象管理者  管理所有的Dao对象，Dao对象中存在着增删改查等API
        daoSession = daoMaster.newSession();
    }

    public static DaoSession getDaoInstant() {
        return daoSession;
    }
}
