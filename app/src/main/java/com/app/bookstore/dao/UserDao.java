package com.app.bookstore.dao;

import android.database.Cursor;
import android.util.Log;

import com.app.bookstore.db.UserTable;
import com.app.bookstore.util.DbUtil;
import com.app.bookstore.util.LogUtil;

import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.util.List;

import static android.content.ContentValues.TAG;


/**
 * Created by ming on 2018/09/21.
 */

public class UserDao {

    static DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
    static DbManager db = x.getDb(daoConfig);

    public static void save(UserTable user) {
        try {
            db.save(user);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    //根据用户名和密码找用户，用于登录      
    public static int findUserByNameAndPwd(String mobile, String password) {
        int result = 0;
        try {
            List<UserTable> userTables = db.selector(UserTable.class).where("mobile_phone", "=", mobile).and("password", "=", password).findAll();
            for (UserTable userTable : userTables) {
                result += 1;
                LogUtil.d("findUserByNameAndPwd", "result=" + result + ":" + userTable.toString());
            }
        } catch (DbException e1) {
            e1.printStackTrace();
        }
        return result;
    }
}
