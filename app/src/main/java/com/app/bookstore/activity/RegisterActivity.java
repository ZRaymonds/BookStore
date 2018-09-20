package com.app.bookstore.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.app.bookstore.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.x;

/**
 * Created by ming on 2018/09/20.
 */

@ContentView(R.layout.activity_register)
public class RegisterActivity extends AppCompatActivity{

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
    }
}
