package com.app.bookstore.view;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.app.bookstore.R;
import com.victor.loading.rotate.RotateLoading;

public class LoadingDialog extends Dialog {

    private Context mContext;
    private View customView;
    private RotateLoading loadView;

    public LoadingDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    /**
     * 初始化自定义的Dialog布局
     *
     * @param msg
     */
    public void initDialog(String msg) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        // 得到加载 view
        customView = inflater.inflate(R.layout.loading_dialog, null);
        // 加载圈
        loadView = (RotateLoading) customView.findViewById(R.id.rotateLoading);
        // 提示文字
        TextView tip = (TextView) customView.findViewById(R.id.loading_tip);
        // 加载圈转动
        loadView.start();
        // 设置提示信息
        tip.setText(msg);

        setContentView(customView);
    }

}
