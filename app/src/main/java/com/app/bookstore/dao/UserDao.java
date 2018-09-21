package com.app.bookstore.dao;

import com.app.bookstore.db.UserTable;
import com.app.bookstore.util.DbUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;


/**
 * Created by ming on 2018/09/21.
 */

public class UserDao {

    static DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
    static DbManager db = x.getDb(daoConfig);

    public static void save(UserTable user){
        try {
            db.save(user);
        }catch (DbException e){
            e.printStackTrace();
        }
    }

}
