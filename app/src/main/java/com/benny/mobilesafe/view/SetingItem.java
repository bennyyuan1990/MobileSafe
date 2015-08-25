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
public class SetingItem extends RelativeLayout {

    private TextView tvTitle;
    private TextView tvDesc;
    private CheckBox ckCheck;

    private String mDescOn;
    private  String mDescOff;

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener;

    public SetingItem(Context context) {
        this(context, null);
    }

    public SetingItem(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SetingItem(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        initView(context);
        initAttrs(attrs, defStyle);
    }


    /**
     * 初始化设置选项的视图
     * @param context
     */
    private void initView(Context context){
       /* View view=  View.inflate(context, R.layout.view_setting_item, this);*/
        View view=  LayoutInflater.from(context).inflate(R.layout.view_setting_item, this,true);

        tvTitle = (TextView) view.findViewById(R.id.tv_setingitem_title);
        tvDesc = (TextView) view.findViewById(R.id.tv_setingitem_desc);
        ckCheck = (CheckBox) view.findViewById(R.id.ck_setingitem_check);

        ckCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (ckCheck.isChecked()) tvDesc.setText(mDescOn);
                else tvDesc.setText(mDescOff);

                if(mOnCheckedChangeListener !=null) mOnCheckedChangeListener.onCheckedChanged(buttonView,isChecked);
            }
        });
        setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ckCheck.setChecked(!ckCheck.isChecked());
            }
        });
    }

    /**
     * 初始化视图的自定义属性
     * @param attrs
     * @param defStyle
     */
    private void initAttrs(AttributeSet attrs, int defStyle) {
        final TypedArray atts = getContext().obtainStyledAttributes(attrs, R.styleable.SetingItem, defStyle, 0);

        tvTitle.setText(atts.getString(R.styleable.SetingItem_itemTitle));
        mDescOn = atts.getString(R.styleable.SetingItem_descOn);
        mDescOff = atts.getString(R.styleable.SetingItem_descOff);
        ckCheck.setChecked(atts.getBoolean(R.styleable.SetingItem_check, false));
        if(ckCheck.isChecked()) tvDesc.setText(mDescOn);
        else  tvDesc.setText(mDescOff);

        atts.recycle();
    }


    /**
     * 获取选项选中状态
     * @return
     */
    public boolean isCheck() {
        return  ckCheck.isChecked();
    }

    /**
     * 设置选项设置值
     * @param check
     * @return
     */
    public  void setCheck(boolean check) {
        ckCheck.setChecked(check);
    }

    /**
     * 设置选项值改变时，监听事件
     * @param listener
     */
    public void setOnCheckedChangeListener(CompoundButton.OnCheckedChangeListener listener) {
        mOnCheckedChangeListener = listener;
    }




}
