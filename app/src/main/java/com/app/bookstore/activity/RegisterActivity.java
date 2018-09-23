package com.app.bookstore.activity;

import android.animation.ValueAnimator;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookstore.R;
import com.app.bookstore.dao.UserDao;
import com.app.bookstore.db.UserTable;
import com.app.bookstore.util.DbUtil;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

/**
 * Created by ming on 2018/09/20.
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity{

    @ViewInject(R.id.et_register_username)
    EditText et_register_username;

    @ViewInject(R.id.et_register_password)
    EditText et_register_password;

    @ViewInject(R.id.et_register_email)
    EditText et_register_email;

    @ViewInject(R.id.btn_register)
    Button btn_register;

    @ViewInject(R.id.back)
    ImageView back;

    @ViewInject(R.id.btn_selectAll)
    Button btn_selectAll;

    @ViewInject(R.id.tv_showUser)
    TextView tv_showUser;

    private String registerUrl = "http://192.168.1.138:8080/api/user/register";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }


    @Event({R.id.btn_register,R.id.back,R.id.btn_selectAll})
    private void onClick(View v){
        switch (v.getId()){
            case R.id.btn_register:
                registerUser();
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                break;
            case R.id.btn_selectAll:
                    registerSelect();
                break;
        }
    }

    private void registerSelect()  {
        DbManager.DaoConfig daoConfig = DbUtil.getDaoConfig();
        DbManager db = x.getDb(daoConfig);
        try {
            List<UserTable> user = db.findAll(UserTable.class);
            Log.d("TAG",user.toString());
            tv_showUser.setText("已注册用户"+user.toString());
        }catch (DbException e){
            e.printStackTrace();
        }

    }

    private void registerUser() {
        UserTable user = new UserTable();
        user.setMobile_phone(et_register_username.getText().toString().trim());
        user.setPassword(et_register_password.getText().toString().trim());
        user.setEmail(et_register_email.getText().toString().trim());
        UserDao.save(user);
        RequestParams params = new RequestParams(registerUrl);
        params.addBodyParameter("mobile_phone",et_register_username.getText().toString().trim());
        params.addBodyParameter("password",et_register_password.getText().toString().trim());
        params.addBodyParameter("email",et_register_email.getText().toString().trim());
        x.http().post(params, new Callback.CacheCallback<String>() {
            @Override
            public void onSuccess(String result) {
                Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
                Log.d("TAG",result);
            }

            @Override
            public void onError(Throwable ex, boolean isOnCallback) {
                Toast.makeText(RegisterActivity.this,ex.toString(),Toast.LENGTH_SHORT).show();
                Log.d("TAG",ex.toString());
            }

            @Override
            public void onCancelled(CancelledException cex) {

            }

            @Override
            public void onFinished() {
            }

            @Override
            public boolean onCache(String result) {
                return false;
            }
        });
    }
}
