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
                    .setDbName("user.db")
                    .setDbVersion(1)
                    .setAllowTransaction(true)
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }

}
