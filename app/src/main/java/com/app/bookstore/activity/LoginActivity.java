package com.app.bookstore.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.bookstore.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by ming on 2018/09/20.
 */

@ContentView(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity{

    @ViewInject(R.id.tv_userRegister)
    TextView tv_userRegister;

    @ViewInject(R.id.back)
    ImageView back;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }



    @Event({R.id.tv_userRegister,R.id.back})
    private void onClick(View v){
        switch (v.getId()){
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
}
