package com.app.bookstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.google.gson.Gson;

import org.xutils.common.Callback;
import org.xutils.http.RequestParams;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by ming on 2018/09/20.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity{

    @ViewInject(R.id.LayoutName)
    TextInputLayout LayoutName;

    @ViewInject(R.id.LayoutPassword)
    TextInputLayout LayoutPassword;

    @ViewInject(R.id.et_login_username)
    EditText et_login_username;

    @ViewInject(R.id.et_login_password)
    EditText et_login_password;

    @ViewInject(R.id.tv_userRegister)
    TextView tv_userRegister;

    @ViewInject(R.id.back)
    ImageView back;

    @ViewInject(R.id.btn_login)
    Button btn_login;

    private String loginUrl = "http://192.168.1.138:8080/api/user/login";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }



    @Event({R.id.tv_userRegister,R.id.back,R.id.btn_login})
    private void onClick(View v){
        String mobile = et_login_username.getText().toString();
        String password = et_login_password.getText().toString();
        switch (v.getId()){
            case R.id.btn_login:
                login(mobile,password);
                break;
            case R.id.tv_userRegister:
                startActivity(new Intent(this,RegisterActivity.class));
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                break;
            case R.id.back:
                finish();
                overridePendingTransition(R.anim.anim_in,R.anim.anim_out);
                break;
        }
    }

    private void login(final String mobile, final String password) {
        onEffectName(mobile);
        onEffectPassword(password);
        if (validateAccount(mobile)&&validatePassword(password)){
            RequestParams params = new RequestParams(loginUrl);
            params.addBodyParameter("mobile_phone",mobile);
            params.addBodyParameter("password",password);
            x.http().post(params, new Callback.CacheCallback<String>() {
                @Override
                public void onSuccess(String result) {
                    Gson gson = new Gson();
                    MsgBean msgBean = gson.fromJson(result,MsgBean.class);
                    Toast.makeText(LoginActivity.this,msgBean.getMsg(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onError(Throwable ex, boolean isOnCallback) {
                    Toast.makeText(LoginActivity.this,"账号或密码错误",Toast.LENGTH_SHORT).show();
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

    /**
     * 显示错误提示，并获取焦点
     * @param textInputLayout
     * @param error
     */
    private void showError(TextInputLayout textInputLayout,String error){
        textInputLayout.setError(error);
        textInputLayout.getEditText().setFocusable(true);
        textInputLayout.getEditText().setFocusableInTouchMode(true);
        textInputLayout.getEditText().requestFocus();
    }

    /**
     * 验证用户名
     * @param mobile
     * @return
     */
    private boolean validateAccount(String mobile){
        boolean b = isPhoneNumber(mobile);
        if (mobile.isEmpty()){
            showError(LayoutName,"手机号不能为空");
            return false;
        }else {
            LayoutName.setErrorEnabled(false);
        }
        return true;
    }

    /**
     * 验证密码
     * @param password
     * @return
     */
    private boolean validatePassword(String password){
        if (password.isEmpty()){
            showError(LayoutPassword,"密码不能为空");
            return false;
        } else {
            LayoutPassword.setErrorEnabled(false);
        }
        return true;
    }


    private void onEffectName(final String mobile){
        LayoutName.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                boolean b = isPhoneNumber(mobile);
                if (charSequence.length() < 11 && !b){
                    LayoutName.setError("请输入正确的手机号");
                    LayoutName.setErrorEnabled(true);
                }else {
                    LayoutName.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    private void onEffectPassword(String password){
        LayoutPassword.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.length() < 6 || charSequence.length() >18){
                    LayoutPassword.setError("密码长度为6-18位");
                    LayoutPassword.setErrorEnabled(true);
                }else {
                    LayoutPassword.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

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
