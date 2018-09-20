package com.app.bookstore.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


import com.app.bookstore.R;
import com.app.bookstore.activity.LoginActivity;

import org.xutils.view.annotation.ContentView;

import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

/**
 * Created by ming on 2018/09/18.
 */

@ContentView(R.layout.fragment_book_my)
public class BookMyFragment extends Fragment{

    @ViewInject(R.id.btn_jumpLogin)
    Button btn_jumpLogin;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return x.view().inject(this,inflater,container);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Event(R.id.btn_jumpLogin)
    private void onClick(View v){
        switch (v.getId()){
            case R.id.btn_jumpLogin:
                startActivity(new Intent(getActivity(),LoginActivity.class));
                break;
        }
    }

}
