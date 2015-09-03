package com.benny.mobilesafe.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.benny.mobilesafe.R;

/**
 * TODO: document your custom view class.
 */
public class SetupItem extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDesc;


    public SetupItem(Context context) {
        this(context, null);
    }

    public SetupItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetupItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        initAttrs(attrs, defStyle);
    }


    /**
     * 初始化设置选项的视图
     *
     * @param context
     */
    private void initView(Context context) {
        View view = LayoutInflater.from(context).inflate(R.layout.view_setup_item, this, true);

        tvTitle = (TextView) view.findViewById(R.id.tv_setupitem_title);
        tvDesc = (TextView) view.findViewById(R.id.tv_setupitem_desc);

    }

    /**
     * 初始化视图的自定义属性
     *
     * @param attrs
     * @param defStyle
     */
    private void initAttrs(AttributeSet attrs, int defStyle) {
        TypedArray atts = getContext().obtainStyledAttributes(attrs, R.styleable.SetupItem, defStyle, 0);

        tvTitle.setText(atts.getString(R.styleable.SetupItem_setupIitle));
        tvDesc.setText(atts.getString(R.styleable.SetupItem_desc));
        atts.recycle();
    }


    public String getDesc() {
        return tvDesc.getText().toString();
    }


    public void setDesc(String desc) {
        tvDesc.setText(desc);
    }


}
