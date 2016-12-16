package com.demo.app.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.InputFilter;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.io.Serializable;

import com.demo.app.R;
import com.demo.app.activity.index.CalendarViewActivity;
import com.demo.app.activity.index.DXSBXSKContentListActivity;
import com.demo.app.activity.index.GroupMembersListActivity;
import com.demo.app.activity.index.WorkTicketNameListActivity;

/**
 * 自定义文本输入框2
 *
 * @author S
 */
public class CustomeEditText2 extends LinearLayout {

    private LinearLayout ll;
    private Activity mActivity;
    private Context mContext;
    private EditText mEditText;
    private TextView mTextView, mTextViewUnit;
    private ImageView mImageView;
    private Button mButton;

    public CustomeEditText2(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
    }

    // 导入布局
    protected void inflaterLayout(Context context) {
        ll = (LinearLayout) LayoutInflater.from(context).inflate(
                R.layout.custome_edittext_layout2, this, true);
    }

    public CustomeEditText2(Context context, AttributeSet attrs) {
        super(context, attrs);
        mContext = context;
        mActivity = (Activity) context;
        inflaterLayout(context);
        mEditText = (EditText) ll.findViewById(R.id.custome_editorText2);
        mTextView = (TextView) ll.findViewById(R.id.custome_textView2);
        mTextViewUnit = (TextView) ll.findViewById(R.id.custome_textViewUnit2);
        mButton = (Button) ll.findViewById(R.id.custome_button2);
        mImageView = (ImageView) ll.findViewById(R.id.custome_imageView2);

        TypedArray typeArray = context.obtainStyledAttributes(attrs,
                R.styleable.CustomEditText2Attrs);
        if (typeArray == null) {
            return;
        }
        for (int i = 0; i < typeArray.length(); i++) {
            int attr = typeArray.getIndex(i);
            switch (attr) {
                case R.styleable.CustomEditText2Attrs_chint2:
                    String chint = typeArray.getText(attr).toString();
                    mEditText.setHint(chint);
                    break;
                case R.styleable.CustomEditText2Attrs_edit_maxline:
                    mEditText.setMaxLines(typeArray.getInteger(attr, 1));
                    break;
                case R.styleable.CustomEditText2Attrs_edit_minline:
                    mEditText.setMinLines(typeArray.getInteger(attr, 1));
                    break;
                case R.styleable.CustomEditText2Attrs_edit_singleline:
                    mEditText.setSingleLine(typeArray.getBoolean(attr, true));
                    break;
                case R.styleable.CustomEditText2Attrs_edit_inputtype:
//				mEditText.setMinHeight(300);
                    String type = typeArray.getText(attr).toString();
                    if ("number".equals(type)) {
                        mEditText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
                        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                    } else if ("decimal".equals(type)) {
                        mEditText.setInputType(EditorInfo.TYPE_CLASS_NUMBER | EditorInfo.TYPE_NUMBER_FLAG_DECIMAL);
                        mEditText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(5)});
                    } else {
                        //多行
                        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    }
                    break;
                case R.styleable.CustomEditText2Attrs_t_text2:
                    mTextView.setText(typeArray.getText(attr));
                    break;
                case R.styleable.CustomEditText2Attrs_button_text:
                    mButton.setText(typeArray.getText(attr));
                    break;
                case R.styleable.CustomEditText2Attrs_show_image://列表控件
                    if (typeArray.getBoolean(attr, false)) {
                        mImageView.setVisibility(View.VISIBLE);
                        mImageView.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                showList();
                            }
                        });
                        mEditText.setFocusable(false);
                        //选择的直接选择，不允许手动填写
                        mEditText.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                showList();
                            }
                        });
                    } else {
//					mImageView.setVisibility(View.GONE);
                    }
                    break;
                case R.styleable.CustomEditText2Attrs_show_image_date://时间控件
                    if (typeArray.getBoolean(attr, false)) {
                        mImageView.setVisibility(View.VISIBLE);
                        mImageView.setImageResource(R.drawable.icon_choose_date);
                        mImageView.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                showDate();
                            }
                        });
                        mEditText.setFocusable(false);
                        //选择的直接选择，不允许手动填写
                        mEditText.setOnClickListener(new OnClickListener() {

                            @Override
                            public void onClick(View v) {
                                // TODO Auto-generated method stub
                                showDate();
                            }
                        });
                    } else {
//					mImageView.setVisibility(View.GONE);
                    }
                    break;
                case R.styleable.CustomEditText2Attrs_show_Button:
                    if (typeArray.getBoolean(attr, false)) {
                        mButton.setVisibility(View.VISIBLE);
                        //查看巡视内容
                        if ("pr_new_dnum".equals(ll.getTag())) {
//                            mButton.setOnClickListener(new OnClickListener() {
//
//                                @Override
//                                public void onClick(View v) {
//                                    // TODO Auto-generated method stub
//                                    showXSContent();
//                                }
//                            });
                        } else if ("pm_duty_group".equals(ll.getTag()) || "pm_workconn_noticepersongroup".equals(ll.getTag())
                                || "pm_duty_dutyfz".equals(ll.getTag()) || "pm_duty_zhuduty".equals(ll.getTag())
                                || "pm_duty_fuduty".equals(ll.getTag()) || "pm_duty_shixiperson".equals(ll.getTag())) {
                            mButton.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    showGroupMembers();
                                }
                            });
                            mEditText.setFocusable(false);
                            //选择的直接选择，不允许手动填写
                            mEditText.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    showGroupMembers();
                                }
                            });
                        } else {
                            mButton.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    showList();
                                }
                            });
                            mEditText.setFocusable(false);
                            //选择的直接选择，不允许手动填写
                            mEditText.setOnClickListener(new OnClickListener() {

                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    showList();
                                }
                            });
                        }

                    } else {
//					mButton.setVisibility(View.GONE);
                    }
                    break;
                case R.styleable.CustomEditText2Attrs_show_unit:
                    if (typeArray.getBoolean(attr, false)) {
                        mTextViewUnit.setVisibility(View.VISIBLE);
                    } else {
                        mTextViewUnit.setVisibility(View.GONE);
                    }
                    break;
                case R.styleable.CustomEditText2Attrs_unit_text:
                    mTextViewUnit.setText(typeArray.getText(attr));
                case R.styleable.CustomEditText2Attrs_readonly:
                    if (typeArray.getBoolean(attr, false)) {
                        mEditText.setFocusable(false);
                    }/*else{
                    mEditText.setFocusable(true);
				}*/
                    break;
                default:
                    break;
            }
        }
        typeArray.recycle();
    }

    /**
     * 获取字符串值
     *
     * @return
     */
    public String getText() {
        return mEditText.getText() == null ? "" : mEditText.getText().toString();
    }

    /**
     * 获取整形
     *
     * @return
     */
    public int getIntText() {
        if (mEditText.getText().toString().equals("")) {
            return -1;
        } else {
            return Integer.parseInt(mEditText.getText().toString());
        }
    }

    //设置选中项目值
    public void setText(String text) {
        mEditText.setText(text);
    }

    //tag用于取ID
    public void setEditTextTag(Object tag) {
        mEditText.setTag(tag);
    }

    public int getEditTexTag() {
        Object object = mEditText.getTag() == null ? -1 : mEditText.getTag();
        return (Integer) object;
    }

    public String getStringEditTexTag() {
        Object object = mEditText.getTag() == null ? "-1" : mEditText.getTag();
        return (String) object;
    }

    /**
     * <显示时间控件 请求码 99>
     */
    public void showDate() {
        Intent i = new Intent();
        //获取当前组件id传递后在activity里面找到组件然后赋值
        i.putExtra("editTextId", ll.getId());
        i.setClass(mContext, CalendarViewActivity.class);
        //日期控件请求码99
        mActivity.startActivityForResult(i, 99);
    }

    /**
     * 根据id展示不同的选项 请求码98
     *
     * @param id
     */
    public void showList() {
        Intent i = new Intent();
        i.putExtra("editTextId", ll.getId());
        if (ll.getTag() != null) {
            i.putExtra("editTextTag", ll.getTag().toString());
        }
        i.setClass(mContext, WorkTicketNameListActivity.class);
        mActivity.startActivityForResult(i, 98);
    }

    /**
     * 查看巡视内容
     */
    public void showXSContent() {
        Intent i = new Intent();
        i.setClass(mContext, DXSBXSKContentListActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        mContext.startActivity(i);
    }

    /**
     * 多选选择组员 请求码97
     */
    public void showGroupMembers() {
        Intent i = new Intent();
        i.putExtra("editTextId", ll.getId());
        i.setClass(mContext, GroupMembersListActivity.class);
//		i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//		mContext.startActivity(i);
        mActivity.startActivityForResult(i, 97);
    }
}
