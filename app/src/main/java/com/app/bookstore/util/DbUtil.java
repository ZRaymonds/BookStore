package com.app.bookstore.util;

import android.os.Environment;

import org.xutils.DbManager;

import java.io.File;

/**
 * Created by ming on 2018/09/21.
 */

public class DbUtil {

    static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig() {
        if(daoConfig==null){
            daoConfig=new DbManager.DaoConfig()
                    .setDbName("bookStore.db")
                    .setDbVersion(1)
                    .setAllowTransaction(true)
                    .setDbOpenListener(new DbManager.DbOpenListener() {
                        @Override
                        public void onDbOpened(DbManager db) {
                            // 开启WAL, 对写入加速提升巨大
                            db.getDatabase().enableWriteAheadLogging();
                        }
                    })
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }

}
