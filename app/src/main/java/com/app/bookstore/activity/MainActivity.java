package com.app.bookstore.activity;

import android.annotation.SuppressLint;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.bookstore.R;
import com.app.bookstore.fragment.BookCityFragment;
import com.app.bookstore.fragment.BookIdeaFragment;
import com.app.bookstore.fragment.BookMyFragment;
import com.app.bookstore.fragment.BookSelfFragment;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;


@ContentView(R.layout.activity_main)
public class MainActivity extends FragmentActivity {

    @ViewInject(R.id.llCity)
    LinearLayout llCity;

    @ViewInject(R.id.llSelf)
    LinearLayout llSelf;

    @ViewInject(R.id.llIdea)
    LinearLayout llIdea;

    @ViewInject(R.id.llSettings)
    LinearLayout llSettings;

    @ViewInject(R.id.ivCity)
    ImageView ivCity;

    @ViewInject(R.id.ivSelf)
    ImageView ivSelf;

    @ViewInject(R.id.ivIdea)
    ImageView ivIdea;

    @ViewInject(R.id.ivSettings)
    ImageView ivSettings;

    @ViewInject(R.id.tvCity)
    TextView tvCity;

    @ViewInject(R.id.tvSelf)
    TextView tvSelf;

    @ViewInject(R.id.tvIdea)
    TextView tvIdea;

    @ViewInject(R.id.tvSettings)
    TextView tvSettings;

    @ViewInject(R.id.main_content)
    FrameLayout main_content;

    private ImageView ivCurrent;

    private TextView tvCurrent;

    private FragmentManager fragmentManager;
    private BookCityFragment bookCityFragment;
    private BookSelfFragment bookSelfFragment;
    private BookIdeaFragment bookIdeaFragment;
    private BookMyFragment bookMyFragment;

    private long lastBackTime = 0;
    private long currentBackTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);

        initView();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    protected void onSaveInstanceState(Bundle outState) {
//        super.onSaveInstanceState(outState);
    }

    private void initView() {

        ivCity.setSelected(true);
        tvCity.setSelected(true);
        ivCurrent = ivCity;
        tvCurrent = tvCity;

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        bookCityFragment = new BookCityFragment();
        fragmentTransaction.add(R.id.main_content, bookCityFragment);
        fragmentTransaction.commit();

    }

    @Event({R.id.llCity, R.id.llSelf, R.id.llIdea, R.id.llSettings})
    private void onClick(View v) {
        ivCurrent.setSelected(false);
        tvCurrent.setSelected(false);
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        hideAllFragment(transaction);
        switch (v.getId()) {
            case R.id.llCity:
                ivCity.setSelected(true);
                ivCurrent = ivCity;
                tvCity.setSelected(true);
                tvCurrent = tvCity;
                if (bookCityFragment == null) {
                    bookCityFragment = new BookCityFragment();
                    transaction.add(R.id.main_content, bookCityFragment);
                } else {
                    transaction.show(bookCityFragment);
                }
                break;
            case R.id.llSelf:
                ivSelf.setSelected(true);
                ivCurrent = ivSelf;
                tvSelf.setSelected(true);
                tvCurrent = tvSelf;
                if (bookSelfFragment == null) {
                    bookSelfFragment = new BookSelfFragment();
                    transaction.replace(R.id.main_content, bookSelfFragment);
                } else {
                    transaction.show(bookSelfFragment);
                }
                break;
            case R.id.llIdea:
                ivIdea.setSelected(true);
                ivCurrent = ivIdea;
                tvIdea.setSelected(true);
                tvCurrent = tvIdea;
                if (bookIdeaFragment == null) {
                    bookIdeaFragment = new BookIdeaFragment();
                    transaction.add(R.id.main_content, bookIdeaFragment);
                } else {
                    transaction.show(bookIdeaFragment);
                }
                break;
            case R.id.llSettings:
                ivSettings.setSelected(true);
                ivCurrent = ivSettings;
                tvSettings.setSelected(true);
                tvCurrent = tvSettings;
                if (bookMyFragment == null) {
                    bookMyFragment = new BookMyFragment();
                    transaction.replace(R.id.main_content, bookMyFragment);
                } else {
                    transaction.show(bookMyFragment);
                }
                break;
            default:
                break;
        }
        transaction.commit();
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideAllFragment(FragmentTransaction transaction) {
        if (bookCityFragment != null) {
            transaction.hide(bookCityFragment);
        }
        if (bookSelfFragment != null) {
            transaction.hide(bookSelfFragment);
        }
        if (bookIdeaFragment != null) {
            transaction.hide(bookIdeaFragment);
        }
        if (bookMyFragment != null) {
            transaction.hide(bookMyFragment);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            currentBackTime = System.currentTimeMillis();
            if (currentBackTime - lastBackTime > 2 * 1000) {
                Toast.makeText(this, "再按一次返回键退出", Toast.LENGTH_SHORT).show();
                lastBackTime = currentBackTime;
            } else {
                finish();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
