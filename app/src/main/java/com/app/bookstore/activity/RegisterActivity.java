package com.app.bookstore.activity;

import android.content.Intent;
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
import com.app.bookstore.bean.MsgBean;
import com.app.bookstore.dao.UserDao;
import com.app.bookstore.db.UserTable;
import com.app.bookstore.util.DbUtil;
import com.google.gson.Gson;

import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
        String mobile = et_register_username.getText().toString();
        String passwrod = et_register_password.getText().toString();
        switch (v.getId()){
            case R.id.btn_register:
                boolean b = isPhoneNumber(mobile);
                if (mobile.isEmpty() || passwrod.isEmpty()){
                    Toast.makeText(this,"用户名或密码不能为空",Toast.LENGTH_SHORT).show();
                }else if (!b){
                    Toast.makeText(this,"输入的手机号有误",Toast.LENGTH_SHORT).show();
                }else if (passwrod.length() < 6){
                    Toast.makeText(this,"密码不能少于六位数",Toast.LENGTH_SHORT).show();
                }else {
                    registerUser(mobile,passwrod);
                }
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

    private void registerUser(String mobile, String passwrod) {
        final UserTable user = new UserTable();
        user.setMobile_phone(mobile);
        user.setPassword(passwrod);
        user.setEmail(et_register_email.getText().toString().trim());
        UserDao.save(user);
        RequestParams params = new RequestParams(registerUrl);
        params.addBodyParameter("mobile_phone",mobile);
        params.addBodyParameter("password",passwrod);
        params.addBodyParameter("email",et_register_email.getText().toString().trim());
        x.http().post(params, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    MsgBean msgBean = gson.fromJson(result,MsgBean.class);
                    Toast.makeText(RegisterActivity.this,msgBean.getMsg(),Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(RegisterActivity.this,LoginActivity.class));
                    finish();
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

    private boolean isPhoneNumber(String phoneStr) {
        //定义电话格式的正则表达式
        String regex = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        //设定查看模式
        Pattern p = Pattern.compile(regex);
        //判断Str是否匹配，返回匹配结果
        Matcher m = p.matcher(phoneStr);
        return m.find();
    }

}
