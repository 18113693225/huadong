package com.demo.app.view;

import com.demo.app.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.text.method.DigitsKeyListener;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
/**
 * 自定义文本输入框
 * @author S
 *
 */
public class CustomeEditText extends LinearLayout {

	private LinearLayout ll;
	private Context mContext;
	private EditText mEditText;
	private Button mButton;
	private boolean allow_null = true;

	public CustomeEditText(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	// 导入布局
	protected void inflaterLayout(Context context) {
		ll = (LinearLayout) LayoutInflater.from(context).inflate(
				R.layout.custome_edittext_layout, this, true);
	}

	public CustomeEditText(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		inflaterLayout(context);
		mEditText = (EditText) ll.findViewById(R.id.custome_editorText);
		//监听焦点,提示不允许为空
		mEditText.setOnFocusChangeListener(new OnFocusChangeListener() {
			
			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				if(!hasFocus&&!allow_null){
					if("".equals(getText())){
						Toast.makeText(mContext, "此项不允许为空！", Toast.LENGTH_SHORT).show();						
					}
				}
			}
		});
		mButton = (Button) ll.findViewById(R.id.custome_delete);
		mButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!"".equals(mEditText.getText())) {
					mEditText.setText("");
				}
			}
		});
		TypedArray typeArray = context.obtainStyledAttributes(attrs,
				R.styleable.CustomEditTextAttrs);
		if (typeArray == null) {
			return;
		}
		for (int i = 0; i < typeArray.length(); i++) {
			int attr = typeArray.getIndex(i);
			switch (attr) {
			case R.styleable.CustomEditTextAttrs_chint:
				String chint = typeArray.getText(attr).toString();
				mEditText.setHint(chint);
				break;
			case R.styleable.CustomEditTextAttrs_allow_null:
				allow_null = typeArray.getBoolean(attr, true);
				break;
			case R.styleable.CustomEditTextAttrs_edit_inputtype1:
				String type = typeArray.getString(attr).toString();
				if("number".equals(type)){
					mEditText.setInputType(EditorInfo.TYPE_CLASS_NUMBER);
//					mEditText.setKeyListener(DigitsKeyListener.getInstance("0123456789xyzXYZ"));
				}
				Log.e("++++++", type);
				break;
			default:
				break;
			}
		}
		typeArray.recycle();
	}
	public String getText(){
		return mEditText.getText()==null?"":mEditText.getText().toString();
	}
}
